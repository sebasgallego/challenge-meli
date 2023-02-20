package com.challenge.meli.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.challenge.meli.R


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        // TODO: Use the ViewModel
    }

}