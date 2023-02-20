package com.challenge.meli.ui.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.meli.R
import com.challenge.meli.ui.search.adapter.SearchAdapter
import com.challenge.meli.databinding.FragmentSearchBinding
import com.challenge.meli.ui.product.data.model.Product
import com.challenge.meli.utils.LogHelper
import com.challenge.meli.utils.ViewHelper
import com.challenge.meli.utils.recycler.RecyclerItemClickListener
import java.util.*

class SearchFragment : Fragment() {

    //View
    private var _binding: FragmentSearchBinding? = null

    // Use the 'by activityViewModels()' for all fragments
    private lateinit var viewModel: SearchViewModel

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        editTextSearch()
        setOnClickClearEditTextSearch()
        initRecyclerView()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Observe get list products
     */
    private fun setupObservers() {

        //Observe get list products
        viewModel.getProductsResponseLiveData()!!.observe(viewLifecycleOwner) { dataResponse ->
            if (dataResponse!!.results.size > 0) {
                productList.clear()
                productList.addAll(dataResponse.results)
                adapterDate!!.newItems(productList)
            }
        }

        //Observe error msg when get list products
        viewModel.getErrorResponseLiveData()!!.observe(viewLifecycleOwner) { responseError ->
            LogHelper().saveLogError(errorResponse = responseError!!)
            ViewHelper(requireActivity()).showMsgError(responseError)
        }

    }

    /**
     * go to next screen productFragment
     */
    fun goToNextScreen(value: String) {
        val bundle = bundleOf(Intent.EXTRA_TEXT to value)
        findNavController().navigate(R.id.action_searchFragment_to_productFragment, bundle)
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
                       // binding.editTextSearch.setText("")
                        goToNextScreen(productList[position].title)
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
    private fun setOnClickClearEditTextSearch() {
        binding.imageViewClear.setOnClickListener {
            binding.editTextSearch.setText("")
        }
    }

    /**
     * init text search
     */
    private fun editTextSearch() {

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
                                viewModel.getProducts(str)
                            }
                        }
                    },
                    DELAY
                )
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {}
        })
    }
}