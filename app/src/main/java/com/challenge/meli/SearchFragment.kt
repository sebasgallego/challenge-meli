package com.challenge.meli

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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

    private var navController: NavController? = null
    private var myContext: FragmentActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        initNavController()
        textSearch()
        initRecyclerView()
        Log.e("DEBUG", "onCreateView")
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("DEBUG", "onActivityCreated")
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

    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }

    /**
     * init Nav Controller
     */
    private fun initNavController() {
        val fragManager: FragmentManager = myContext!!.supportFragmentManager
        val navHostFragment =
            fragManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
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
                        navController!!.navigate(R.id.productFragment, bundle)
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