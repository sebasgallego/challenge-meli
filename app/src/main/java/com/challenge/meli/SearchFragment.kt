package com.challenge.meli

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.meli.adapter.SearchAdapter
import com.challenge.meli.databinding.FragmentSearchBinding
import com.challenge.meli.product.model.Product
import kotlinx.coroutines.launch
import java.util.*

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    //View
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    //List view
    private var productList = mutableListOf<Product>()
    private var adapterDate: SearchAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        textSearch()
        initRecyclerView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.initRepository()
        // TODO: Use the ViewModel
        //Observe get list products
        viewModel.getProductsResponseLiveData()!!.observe(requireActivity()) { dataResponse ->
            if (dataResponse != null) {
                if (dataResponse.results.size > 0) {
                    productList.clear()
                    productList.addAll(dataResponse.results)
                    adapterDate!!.newItems(productList)
                }
            }
        }

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
    }

    private fun textSearch() {
        binding.imageViewClear.setOnClickListener {
            binding.editTextSearch.setText("")
        }
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {

            var isTyping = false
            private var timer: Timer = Timer()
            private val DELAY: Long = 650 // milliseconds

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