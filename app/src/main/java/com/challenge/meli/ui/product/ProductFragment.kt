package com.challenge.meli.ui.product

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.meli.R
import com.challenge.meli.data.model.Product
import com.challenge.meli.databinding.FragmentProductBinding
import com.challenge.meli.ui.product.adapter.ProductAdapter
import com.challenge.meli.utils.ViewHelper
import com.challenge.meli.utils.recycler.RecyclerViewEmptyRetryGroup
import java.util.ArrayList


class ProductFragment : Fragment(), ProductAdapter.ProductItemListener {

    //View model and binding
    private var binding: FragmentProductBinding? = null

    //Owner view model
    val productViewModel: ProductViewModel by navGraphViewModels(R.id.nav_product)

    //List view
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentProductBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = productViewModel
            productFragment = this@ProductFragment
        }
        initRecyclerView()
        setupObservers()
        getDataArgs()
        onClickRetry()
    }

    /**
     * Observe get list products
     */
    private fun setupObservers() {

        //Observe loading when get list products
        productViewModel.loading.observe(viewLifecycleOwner) {
            if (it)
                binding!!.contentRecyclerView.rvGroup.loading()
        }

        //Observe get list products
        productViewModel.productLiveData.observe(viewLifecycleOwner) { dataResponse ->
            if (dataResponse.results.size > 0) {
                adapter.newItems(ArrayList(dataResponse.results))
                binding!!.contentRecyclerView.rvGroup.success()
            } else {
                val emptyData: String = getString(R.string.empty_data)
                binding!!.contentRecyclerView.rvGroup.empty(emptyData)
            }
        }

        //Observe error msg when get list products
        productViewModel.errorCode.observe(viewLifecycleOwner) { responseCode ->
            if (responseCode != null) {
                binding!!.contentRecyclerView.rvGroup.retry(
                    ViewHelper(requireActivity()).processMsgError(
                        responseCode
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /**
     * init Recycler View
     */
    @SuppressLint("SetTextI18n")
    private fun initRecyclerView() {
        adapter = ProductAdapter(this)
        binding!!.contentRecyclerView.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding!!.contentRecyclerView.recyclerView.adapter = adapter
    }

    /**
     * Get data from arguments and request get
     */
    fun getDataArgs() {
        arguments?.getString(Intent.EXTRA_TEXT)?.let {
            if (!productViewModel.isSuccess)
                productViewModel.getProducts(it)
        }
    }

    /**
     * Click retry when failed request
     */
    private fun onClickRetry() {
        binding!!.contentRecyclerView.rvGroup.setOnRetryClick(object :
            RecyclerViewEmptyRetryGroup.OnRetryClick {
            override fun onRetry() {
                getDataArgs()
            }
        })
    }

    /**
     * Go to next screen detail fragment
     */
    private fun goToNextScreen(product: Product) {
        productViewModel.setProductSelected(product)
        findNavController().navigate(R.id.action_productFragment_to_detailFragment)
    }

    /**
     * Go to detail fragment from product selected
     */
    override fun onClickedProduct(product: Product) {
        goToNextScreen(product)
    }

}