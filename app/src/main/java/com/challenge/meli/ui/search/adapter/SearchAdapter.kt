package com.challenge.meli.ui.search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.meli.R
import com.challenge.meli.data.model.Product
import com.challenge.meli.databinding.ListItemSearchBinding

class SearchAdapter(private val listener: SearchItemListener) : RecyclerView.Adapter<SearchViewHolder>() {

    private var itemList: MutableList<Product> = ArrayList()

    interface SearchItemListener {
        fun onClickedProduct(title: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding: ListItemSearchBinding = ListItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding, listener)
    }

    /**
     * on Bind ViewHolder
     */
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    /**
     * new Items
     */
    @SuppressLint("NotifyDataSetChanged")
    fun newItems(items: MutableList<Product>) {
        this.itemList.clear()
        this.itemList.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * get Item Count
     */
    override fun getItemCount(): Int = itemList.size
}