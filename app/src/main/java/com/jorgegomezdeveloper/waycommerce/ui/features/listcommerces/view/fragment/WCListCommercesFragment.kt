package com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import com.jorgegomezdeveloper.waycommerce.R
import com.jorgegomezdeveloper.waycommerce.databinding.FragmentListCommercesBinding
import com.jorgegomezdeveloper.waycommerce.model.Commerce
import com.jorgegomezdeveloper.waycommerce.ui.base.WCBaseViewModelFragment
import com.jorgegomezdeveloper.waycommerce.ui.features.listcommerces.viewmodel.WCListCommercesViewModel
import com.jorgegomezdeveloper.waycommerce.usercases.GetCommerces
import kotlinx.android.synthetic.main.fragment_list_commerces.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class WCListCommercesFragment: WCBaseViewModelFragment<WCListCommercesViewModel>() {

    companion object {
        const val TAG_FRAGMENT = "WCListCommercesFragment"
    }

    // View Binding
    private var binding: FragmentListCommercesBinding? = null

    // DEPENDENCY INJECTS

    // View Models and Fragments
    private val wcListCommercesViewModel: WCListCommercesViewModel by viewModel()
    // Use Cases
    private val getCommerces: GetCommerces by inject()
    // Utils

    // Managers

    // Others

    override fun initialize() {
        Log.i("TAG_INITIALIZE", TAG_FRAGMENT)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentListCommercesBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_list_commerces
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        initializeListeners()
        observeData()
    }

    override fun initializeViews() {

    }

    override fun initializeListeners() {

        introDataUseCaseButton.setOnClickListener {
            wcListCommercesViewModel.getCommerces(getCommerces, this)}
    }

    private fun observeData() {

        wcListCommercesViewModel.getCommercesMutableLiveData().observe(
            viewLifecycleOwner, {

                if (wcListCommercesViewModel.getCommercesMutableLiveData().value != null) {

                    val commerces: List<Commerce> =
                        wcListCommercesViewModel
                            .getCommercesMutableLiveData().value as List<Commerce>

                }
                wcListCommercesViewModel.setCommercesMutableLiveData(MutableLiveData())
            })
    }
}