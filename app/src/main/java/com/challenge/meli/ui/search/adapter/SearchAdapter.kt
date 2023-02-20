package com.challenge.meli.ui.search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.meli.R
import com.challenge.meli.data.model.Product

class SearchAdapter(
    private val context: Context
) : RecyclerView.Adapter<SearchViewHolder>() {

    private var itemList: MutableList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(
            layoutInflater.inflate(
                R.layout.list_item_search,
                parent,
                false
            )
        )
    }

    /**
     * on Bind ViewHolder
     */
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, this.context)
    }

    /**
     * new Items
     */
    @SuppressLint("NotifyDataSetChanged")
    fun newItems(items: MutableList<Product>) {
        itemList = items
        notifyDataSetChanged()
    }

    /**
     * delete Item
     */
    fun deleteItem(position: Int) {
        itemList.removeAt(position)
    }

    /**
     * add Item
     */
    fun addItem(service: Product) {
        itemList.add(service)
    }

    /**
     * get Item Count
     */
    override fun getItemCount(): Int = itemList.size
}