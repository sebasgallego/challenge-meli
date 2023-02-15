package com.challenge.meli

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.meli.adapter.SearchAdapter
import com.challenge.meli.product.model.Product
import com.challenge.meli.databinding.FragmentSearchBinding

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
        processTextSearch()
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
                Log.e("DEBUG:",dataResponse.results.size.toString())
                if (dataResponse.results.size > 0){
                    productList.clear()
                    productList.addAll(dataResponse.results)
                    adapterDate!!.newItems(productList)
                }
            }
            /*if (userResponse != null && userResponse.success!!) {
                preferencesUser.saveLogin(
                    userResponse,
                    true,
                    loginViewModel.mLogin.cellPhone!!.toLong(),
                    loginViewModel.mLogin.code!!.toLong()
                )
                userData = PreferencesUser(requireActivity()).getData()
            } else {
                if (userResponse != null)
                    msgError = userResponse.message!!
                myContext?.toast(msgError)
                binding.activeServiceContent.visibility = View.GONE
            }
            checkServiceActive()*/
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

    private fun processTextSearch() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                val str = s.toString().trim()
                if (str.length > 2) {
                    viewModel.getProducts(str)
                }
            }
        })
    }
}