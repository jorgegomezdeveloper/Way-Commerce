package com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.jorgegomezdeveloper.waycommerce.R
import com.jorgegomezdeveloper.waycommerce.databinding.FragmentListCommercesBinding
import com.jorgegomezdeveloper.waycommerce.model.Commerce
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseViewModelFragment
import com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.view.adapter.WCListCommercesAdapter
import com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.viewmodel.WCListCommercesViewModel
import com.jorgegomezdeveloper.waycommerce.usercases.GetCommerces
import com.jorgegomezdeveloper.waycommerce.util.common.LoadingUtil
import com.jorgegomezdeveloper.waycommerce.util.location.GpsUtil
import com.jorgegomezdeveloper.waycommerce.util.location.LocationUtil
import kotlinx.android.synthetic.main.fragment_list_commerces.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *   @author Jorge G.A.
 *   @since 22/10/2021
 *   @email jorgegomezdeveloper@gmail.com
 *
 *   Fragment class for the views of the list of commerces.
 */
class WCListCommercesFragment: WCBaseViewModelFragment<WCListCommercesViewModel>() {

    companion object {
        const val TAG_FRAGMENT = "WCListCommercesFragment"
    }

// =================================================================================================
// Attributes and Injections
// =================================================================================================

    private val wcListCommercesViewModel: WCListCommercesViewModel by viewModel()
    private val getCommerces: GetCommerces by inject()
    private val gpsUtil: GpsUtil by inject()
    private val locationUtil: LocationUtil by inject()

    private var isFromSpinner: Boolean = false

// =================================================================================================
// Config
// =================================================================================================

    override fun initialize() {
        //Initialize Gps.
        wcListCommercesViewModel.getLocation(gpsUtil, locationUtil, this)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return FragmentListCommercesBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeListeners()
        loadData()
        observeDataCommerces()
    }

// =================================================================================================
// Override methods
// =================================================================================================

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_list_commerces
    }

    override fun initializeListeners() {
        initializeListenerSpinner()
        initializeListenerSwitch()
    }

    private fun initializeListenerSpinner() {

        optionsFilterListSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {

                optionsFilterListSp.setSelection(position)
                //Get category selected.
                val categorySelected: String = parent?.getItemAtPosition(position) as String
                //Get list of commerces filtered by category selected.
                if (wcListCommercesViewModel.getCommercesMutableLiveData().value != null) {
                    getFilteredListCommercesByCategorySelected(categorySelected)
                    isFromSpinner = true
                    orderLocationSw.isChecked = false
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun initializeListenerSwitch() {

        orderLocationSw.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                //Execute get location from gps.
                LoadingUtil.showLoading(activity!!)
                wcListCommercesViewModel.getLocation(
                    gpsUtil,
                    locationUtil,
                    this)
                //Update adapter of list commerces.
                initializeAdapterListCommerces(
                    wcListCommercesViewModel.getCommercesCurrentOrdered())

            } else {
                //Update adapter of list commerces.
                    if (!isFromSpinner) {
                        initializeAdapterListCommerces(
                            wcListCommercesViewModel.getCommercesCurrent()
                        )
                    }
                isFromSpinner = false
            }
        }
    }

    override fun loadData() {
        //Execute get commerces from remote services.
        LoadingUtil.showLoading(activity!!)
        wcListCommercesViewModel.getCommerces(getCommerces, this)
    }

// =================================================================================================
// Private methods
// =================================================================================================

    private fun observeDataCommerces() {

        wcListCommercesViewModel.getCommercesMutableLiveData().observe(
            viewLifecycleOwner, {

                if (wcListCommercesViewModel.getCommercesMutableLiveData().value != null) {

                    val commerces: List<Commerce> =
                        wcListCommercesViewModel
                            .getCommercesMutableLiveData().value as List<Commerce>

                    initializeAdapterListCommerces(commerces)
                }
            })
    }

    /**
     * Get the list of commerces filtered with the category selected.
     * And updated the adapter.
     */
    private fun getFilteredListCommercesByCategorySelected(
        categorySelected: String) {

        //Get list of commerced filtered by category selected.
        val listCommercesByCategory: List<Commerce>? =
            wcListCommercesViewModel.getListCommercesByCategory(categorySelected)
        //Update adapter of list commerces
        initializeAdapterListCommerces(listCommercesByCategory)
    }

    private fun initializeAdapterListCommerces(commerces: List<Commerce>?) {

        if (commerces!!.isNotEmpty()) {

            listCommercesRv.layoutManager = LinearLayoutManager(context)
            listCommercesRv.adapter =
                WCListCommercesAdapter(commerces)
        }
        LoadingUtil.hideLoading(activity!!)
    }
}