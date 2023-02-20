package com.challenge.meli.ui.product

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.meli.R
import com.challenge.meli.databinding.FragmentProductBinding
import com.challenge.meli.ui.product.adapter.ProductAdapter
import com.challenge.meli.data.model.Product
import com.challenge.meli.ui.search.adapter.SearchAdapter
import com.challenge.meli.utils.LogHelper
import com.challenge.meli.utils.ViewHelper
import com.challenge.meli.utils.recycler.RecyclerItemClickListener
import com.challenge.meli.utils.recycler.RecyclerViewEmptyRetryGroup


class ProductFragment : Fragment() {

    //View
    private var binding: FragmentProductBinding? = null
    private val productViewModel: ProductViewModel by activityViewModels()

    //List view
    private var productList = mutableListOf<Product>()
    private var adapterDate: ProductAdapter? = null

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
        //Observe get list products
        productViewModel.getProductsResponseLiveData()!!
            .observe(viewLifecycleOwner) { dataResponse ->
                if (dataResponse != null)
                    if (dataResponse.results.size > 0) {
                        productList.clear()
                        productList.addAll(dataResponse.results)
                        adapterDate!!.newItems(productList)

                        binding!!.contentRecyclerView.rvGroup.success()
                    } else {
                        val emptyData: String = getString(R.string.empty_data)
                        binding!!.contentRecyclerView.rvGroup.empty(emptyData)
                    }
            }

        //Observe error msg when get list products
        productViewModel.getErrorResponseLiveData()!!.observe(viewLifecycleOwner) { responseError ->
            if(responseError != null) {
                val logHelper = LogHelper()
                val viewHelper = ViewHelper(requireActivity())
                logHelper.saveLogError(errorResponse = responseError)
                binding!!
                    .contentRecyclerView
                    .rvGroup
                    .retry(viewHelper.processMsgError(responseError))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        productViewModel.clear()
    }

    /**
     * init Recycler View
     */
    @SuppressLint("SetTextI18n")
    private fun initRecyclerView() {
        adapterDate = ProductAdapter(requireActivity())
        adapterDate!!.newItems(productList)
        binding!!.contentRecyclerView.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding!!.contentRecyclerView.recyclerView.adapter = adapterDate
        binding!!.contentRecyclerView.recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                binding!!.contentRecyclerView.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // do whatever
                        productViewModel.setProductSelected(productList[position])
                        goToNextScreen()
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        // do whatever
                    }
                })
        )
    }

    /**
     * Get data from arguments and request get
     */
    fun getDataArgs() {
        arguments?.getString(Intent.EXTRA_TEXT)?.let {
            if (productList.size == 0) {
                binding!!.contentRecyclerView.rvGroup.loading()
                productViewModel.getProducts(it)
            }
        }
    }

    /**
     * Click retry when failed request
     */
    private fun onClickRetry() {
        binding!!.contentRecyclerView.rvGroup.setOnRetryClick(object :
            RecyclerViewEmptyRetryGroup.OnRetryClick {
            override fun onRetry() {
                binding!!.contentRecyclerView.rvGroup.loading()
                getDataArgs()
            }
        })
    }

    /**
     * Go to next screen detail fragment
     */
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_productFragment_to_detailFragment)
    }

}