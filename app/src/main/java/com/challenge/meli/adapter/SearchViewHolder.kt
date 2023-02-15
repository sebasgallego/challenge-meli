package com.challenge.meli.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.meli.product.model.Product
import com.challenge.meli.databinding.ListItemSearchBinding

class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ListItemSearchBinding.bind(view)

    fun bind(item: Product, context:   Context) {
        Glide.with(context).load(item.thumbnail).into(binding.imageViewProduct)
        binding.textViewTitle.text = item.title
    }

}