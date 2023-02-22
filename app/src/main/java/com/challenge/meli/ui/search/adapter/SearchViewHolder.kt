package com.challenge.meli.ui.search.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.meli.data.model.Product
import com.challenge.meli.databinding.ListItemSearchBinding

class SearchViewHolder(
    private val itemBinding: ListItemSearchBinding,
    private val listener: SearchAdapter.SearchItemListener
) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {
    private lateinit var product: Product

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Product) {
        this.product = item
        Glide.with(itemBinding.root).load(item.thumbnail).circleCrop()
            .into(itemBinding.imageViewProduct)
        itemBinding.textViewTitle.text = item.title
    }

    override fun onClick(p0: View?) {
        listener.onClickedProduct(product.title)
    }

}