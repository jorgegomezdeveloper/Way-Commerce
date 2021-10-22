package com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.jorgegomezdeveloper.waycommerce.R
import com.jorgegomezdeveloper.waycommerce.databinding.FragmentListCommercesBinding
import com.jorgegomezdeveloper.waycommerce.model.Commerce
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseViewModelFragment
import com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.adapter.WCListCommercesAdapter
import com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.viewmodel.WCListCommercesViewModel
import com.jorgegomezdeveloper.waycommerce.usercases.GetCommerces
import com.jorgegomezdeveloper.waycommerce.util.common.LoadingUtil
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

    private var binding: FragmentListCommercesBinding? = null

    private val wcListCommercesViewModel: WCListCommercesViewModel by viewModel()
    private val getCommerces: GetCommerces by inject()

// =================================================================================================
// Config
// =================================================================================================

    override fun initialize() {
        Log.i("TAG_INITIALIZE", TAG_FRAGMENT)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentListCommercesBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        observeData()
    }

// =================================================================================================
// Override methods
// =================================================================================================

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_list_commerces
    }

    override fun loadData() {
        LoadingUtil.showLoading(activity!!)
        wcListCommercesViewModel.getCommerces(getCommerces, this)
    }

// =================================================================================================
// Private methods
// =================================================================================================

    private fun observeData() {

        wcListCommercesViewModel.getCommercesMutableLiveData().observe(
            viewLifecycleOwner, {

                if (wcListCommercesViewModel.getCommercesMutableLiveData().value != null) {

                    val commerces: List<Commerce> =
                        wcListCommercesViewModel
                            .getCommercesMutableLiveData().value as List<Commerce>

                    initializeAdapterListCommerces(commerces)
                }

                wcListCommercesViewModel.setCommercesMutableLiveData(MutableLiveData())
                LoadingUtil.hideLoading(activity!!)
            })
    }

    private fun initializeAdapterListCommerces(commerces: List<Commerce>) {

        if (commerces.isNotEmpty()) {

            listCommercesRv.layoutManager = LinearLayoutManager(context)
            listCommercesRv.adapter =
                WCListCommercesAdapter(commerces)
        }
    }
}