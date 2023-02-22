package com.challenge.meli.ui.product.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.meli.data.model.Product
import com.challenge.meli.databinding.ListItemProductBinding

class ProductAdapter(
    private val listener: ProductItemListener
) : RecyclerView.Adapter<ProductViewHolder>() {

    private var itemList: MutableList<Product> = ArrayList()

    interface ProductItemListener {
        fun onClickedProduct(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding: ListItemProductBinding =
            ListItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, listener)
    }

    /**
     * on Bind ViewHolder
     */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
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
     * get Item Count
     */
    override fun getItemCount(): Int = itemList.size
}