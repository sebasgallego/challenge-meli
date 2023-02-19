package com.challenge.meli

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.meli.adapter.SearchAdapter
import com.challenge.meli.databinding.FragmentSearchBinding
import com.challenge.meli.product.model.Product
import com.challenge.meli.utils.recycler.RecyclerItemClickListener
import java.util.*

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    //View
    private var _binding: FragmentSearchBinding? = null
    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val productViewModel: ProductViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //List view
    private var productList = mutableListOf<Product>()
    private var adapterDate: SearchAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        textSearch()
        initRecyclerView()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Use the ViewModel
        //Observe get list products
        productViewModel.getProductsResponseLiveData()!!.observe(requireActivity()) { dataResponse ->
            if (dataResponse != null) {
                if (dataResponse.results.size > 0) {
                    productList.clear()
                    productList.addAll(dataResponse.results)
                    adapterDate!!.newItems(productList)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_searchFragment_to_productFragment)
    }

    /**
     * init Recycler View
     */
    @SuppressLint("SetTextI18n")
    private fun initRecyclerView() {
        adapterDate = SearchAdapter(requireActivity())
        adapterDate!!.newItems(productList)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapterDate
        binding.recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                binding.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // do whatever
                        binding.editTextSearch.setText("")
                        productViewModel.setNameSelect(productList[position].title)
                        goToNextScreen()
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        // do whatever
                    }
                })
        )
    }

    /**
     * init text search
     */
    private fun textSearch() {
        binding.imageViewClear.setOnClickListener {
            binding.editTextSearch.setText("")
        }
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {

            var isTyping = false
            private var timer: Timer = Timer()
            private val DELAY: Long = 250 // milliseconds

            override fun afterTextChanged(s: Editable) {
                if (!isTyping) {
                    isTyping = true
                }
                timer.cancel()
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            isTyping = false
                            val str = s.toString().trim()
                            if (str.length > 2) {
                                productViewModel.getProducts(str)
                            }
                        }
                    },
                    DELAY
                )
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }
}