package com.challenge.meli.product.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.meli.R
import com.challenge.meli.product.model.Product

class ProductAdapter(
    private val context: Context,
) : RecyclerView.Adapter<ProductViewHolder>() {

    private var itemList: MutableList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(
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
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
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