package com.challenge.meli

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.meli.databinding.FragmentProductBinding
import com.challenge.meli.product.adapter.ProductAdapter
import com.challenge.meli.product.model.Product
import com.challenge.meli.utils.recycler.RecyclerItemClickListener


class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    //View
    private lateinit var binding: FragmentProductBinding
    private lateinit var viewModel: ProductViewModel

    //List view
    private var productList = mutableListOf<Product>()
    private var adapterDate: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater)
        initRecyclerView()
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        viewModel.initRepository()
        // TODO: Use the ViewModel
        // Get products
        getExtraTitle()?.let {
            viewModel.getProducts(it)
            binding.contentRecyclerView.rvGroup.loading()
        }
        //Observe get list products
        viewModel.getProductsResponseLiveData()!!.observe(requireActivity()) { dataResponse ->
            if (dataResponse != null) {
                if (dataResponse.results.size > 0) {
                    productList.clear()
                    productList.addAll(dataResponse.results)
                    adapterDate!!.newItems(productList)
                    updateUI()
                    binding.contentRecyclerView.rvGroup.success()
                }else{
                    val emptyData: String = getString(R.string.empty_data)
                    binding.contentRecyclerView.rvGroup.empty(emptyData)
                }
            }else{
                binding.contentRecyclerView.rvGroup.retry()
            }
        }
    }

    /**
     * init Recycler View
     */
    @SuppressLint("SetTextI18n")
    private fun initRecyclerView() {
        adapterDate = ProductAdapter(requireActivity())
        adapterDate!!.newItems(productList)
        binding.contentRecyclerView.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.contentRecyclerView.recyclerView.adapter = adapterDate
        binding.contentRecyclerView.recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                binding.contentRecyclerView.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // do whatever
                        val bundle = bundleOf(
                            Intent.EXTRA_TEXT to productList[position].title
                        )
                        //view.findNavController().navigate(R.id.productFragment, bundle)
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        // do whatever
                    }
                })
        )
    }

    private fun getExtraTitle(): String? {
        return requireArguments().getString(Intent.EXTRA_TEXT)
    }

    private fun updateUI(){
        val lblResult: String = getString(R.string.lbl_results)
        binding.textViewSize.text = "${productList.size} $lblResult"
    }

}