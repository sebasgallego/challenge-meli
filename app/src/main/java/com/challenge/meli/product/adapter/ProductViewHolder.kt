package com.challenge.meli.product.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.meli.databinding.ListItemProductBinding
import com.challenge.meli.product.model.Product

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ListItemProductBinding.bind(view)

    fun bind(item: Product, context: Context) {
        Glide.with(context).load(item.thumbnail)
            .circleCrop()
            .into(binding.imageViewProduct)
    }

}