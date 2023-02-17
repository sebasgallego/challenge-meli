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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.meli.adapter.SearchAdapter
import com.challenge.meli.product.adapter.ProductAdapter
import com.challenge.meli.databinding.FragmentSearchBinding
import com.challenge.meli.product.model.Product
import com.challenge.meli.utils.recycler.RecyclerItemClickListener
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
        binding.recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                binding.recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // do whatever
                        binding.editTextSearch.setText("")
                        val bundle = bundleOf(
                            Intent.EXTRA_TEXT to productList[position].title
                        )
                        view.findNavController().navigate(R.id.productFragment, bundle)
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
            private val DELAY: Long = 500 // milliseconds

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