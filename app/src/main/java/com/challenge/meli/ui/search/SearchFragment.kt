package com.challenge.meli.ui.search

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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.meli.R
import com.challenge.meli.databinding.FragmentSearchBinding
import com.challenge.meli.ui.search.adapter.SearchAdapter
import com.challenge.meli.utils.ViewHelper
import com.challenge.meli.utils.recycler.RecyclerViewEmptyRetryGroup
import java.util.*

class SearchFragment : Fragment(), SearchAdapter.SearchItemListener {

    //View model and binding
    private var binding: FragmentSearchBinding? = null
    private val viewModel: SearchViewModel by viewModels()

    //List view
    private lateinit var adapter: SearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentSearchBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        editTextSearch()
        setOnClickClearEditTextSearch()
        setupObservers()
        onClickRetry()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /**
     * Observe get list products
     */
    private fun setupObservers() {

        //Observe loading when get list products
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it)
                binding!!.contentRecyclerView.rvGroup.loading()
        }

        //Observe get list products
        viewModel.productLiveData.observe(viewLifecycleOwner) { dataResponse ->
            if (dataResponse!!.results.size > 0) {
                adapter.newItems(ArrayList(dataResponse.results))
                binding!!.contentRecyclerView.rvGroup.success()
            } else {
                val emptyData: String = getString(R.string.empty_data)
                binding!!.contentRecyclerView.rvGroup.empty(emptyData)
            }
        }

        //Observe error msg when get list products
        viewModel.errorCode.observe(viewLifecycleOwner) { responseCode ->
            if (responseCode != null) {
                binding!!.contentRecyclerView.rvGroup.retry(
                    ViewHelper(requireActivity()).processMsgError(
                        responseCode
                    )
                )
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
                viewModel.getProducts(viewModel.oldTextSearch)
            }
        })
    }

    /**
     * go to next screen productFragment
     */
    private fun goToNextScreen(value: String) {
        val bundle = bundleOf(Intent.EXTRA_TEXT to value)
        findNavController().navigate(R.id.action_searchFragment_to_nav_product, bundle)
    }

    /**
     * init Recycler View
     */
    @SuppressLint("SetTextI18n")
    private fun initRecyclerView() {
        adapter = SearchAdapter(this)
        binding!!.contentRecyclerView.recyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.contentRecyclerView.recyclerView.adapter = adapter
    }

    /**
     * init text search
     */
    private fun setOnClickClearEditTextSearch() {
        binding!!.imageViewClear.setOnClickListener {
            binding!!.editTextSearch.setText("")
        }
    }

    /**
     * init text search
     */
    private fun editTextSearch() {
        binding!!.editTextSearch.addTextChangedListener(object : TextWatcher {

            var isTyping = false
            private var timer: Timer = Timer()
            private val DELAY: Long = 200 // milliseconds

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
                                if (viewModel.oldTextSearch != str) {
                                    viewModel.getProducts(str)
                                }
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

    /**
     * Get title from on click item on recycler view
     */
    override fun onClickedProduct(title: String) {
        goToNextScreen(title)
    }

}