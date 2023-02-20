package com.challenge.meli.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.challenge.meli.R
import com.challenge.meli.databinding.FragmentDetailBinding
import com.challenge.meli.databinding.FragmentProductBinding
import com.challenge.meli.ui.product.ProductViewModel


class DetailFragment : Fragment() {

    //View
    private var binding: FragmentDetailBinding? = null
    // For all fragments this activity
    private val detailViewModel: ProductViewModel by activityViewModels()

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