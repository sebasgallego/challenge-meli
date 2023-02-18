package com.challenge.meli

import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.challenge.meli.product.model.Product
import com.challenge.meli.utils.GlobalsVar.EXTRA_PRODUCT_OBJECT


class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*val extras = requireActivity().intent.extras
        if (extras != null) {
            val p = requireActivity().intent.getSerializableExtra(EXTRA_PRODUCT_OBJECT) as Product? //Obtaining data
            Log.e("DEBUG",p!!.title)
        }*/
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}