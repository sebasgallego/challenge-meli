package com.challenge.meli.ui.search.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.meli.data.model.Product
import com.challenge.meli.databinding.ListItemSearchBinding

class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ListItemSearchBinding.bind(view)

    fun bind(item: Product, context: Context) {
        Glide.with(context).load(item.thumbnail).circleCrop().into(binding.imageViewProduct)
        binding.textViewTitle.text = item.title
    }

}