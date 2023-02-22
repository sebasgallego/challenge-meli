package com.challenge.meli.ui.product.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.challenge.meli.R
import com.challenge.meli.databinding.FragmentDetailBinding
import com.challenge.meli.ui.product.ProductViewModel


class DetailFragment : Fragment() {

    //View model and binding
    private var binding: FragmentDetailBinding? = null
    // Child view model for this fragments
    private val detailViewModel: ProductViewModel by navGraphViewModels(R.id.nav_product)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentDetailBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = detailViewModel
            detailFragment = this@DetailFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}