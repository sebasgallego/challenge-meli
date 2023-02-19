package com.challenge.meli

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.meli.databinding.FragmentProductBinding
import com.challenge.meli.product.adapter.ProductAdapter
import com.challenge.meli.product.model.Product
import com.challenge.meli.utils.recycler.RecyclerItemClickListener


class ProductFragment : Fragment() {

    // Binding object instance corresponding to the fragment_product.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var _binding: FragmentProductBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val productViewModel: ProductViewModel by activityViewModels()

    //List view
    private var productList = mutableListOf<Product>()
    private var adapterDate: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initRecyclerView()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = productViewModel
            productFragment = this@ProductFragment
        }

        // TODO: Use the ViewModel
        //Observe get list products
        productViewModel.getProductsResponseLiveData()!!.observe(requireActivity()) { dataResponse ->
            if (dataResponse != null) {
                if (dataResponse.results.size > 0) {
                    productList.clear()
                    productList.addAll(dataResponse.results)
                    adapterDate!!.newItems(productList)
                    productViewModel.setSizeProducts(productList.size)
                   // binding.contentRecyclerView.rvGroup.success()
                } else {
                    val emptyData: String = getString(R.string.empty_data)
                    binding.contentRecyclerView.rvGroup.empty(emptyData)
                }
            } else {
                binding.contentRecyclerView.rvGroup.retry()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                        goToNextScreen()
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        // do whatever
                    }
                })
        )
    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_productFragment_to_detailFragment)
    }

}