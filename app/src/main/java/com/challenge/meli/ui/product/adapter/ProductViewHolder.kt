package com.challenge.meli.ui.product.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.meli.data.model.Product
import com.challenge.meli.databinding.ListItemProductBinding
import com.challenge.meli.utils.NumberHelper

class ProductViewHolder(
    private val itemBinding: ListItemProductBinding,
    private val listener: ProductAdapter.ProductItemListener
) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {
    private lateinit var product: Product

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Product) {
        this.product = item
        Glide.with(itemBinding.root).load(item.thumbnail).centerInside()
            .into(itemBinding.imageViewProduct)
        itemBinding.textViewTitle.text = item.title.take(60)
        itemBinding.textViewPrice.text = "$ ${NumberHelper().parseAmountToCOP(item.price)}"
        if (item.installments != null) itemBinding.textViewInstallments.text =
            "${item.installments!!.quantity}x $${NumberHelper().parseAmountToCOP(item.installments!!.amount)}"
        itemBinding.textViewQuantity.text = "${item.availableQuantity}"
    }

    override fun onClick(p0: View?) {
        listener.onClickedProduct(product)
    }

}