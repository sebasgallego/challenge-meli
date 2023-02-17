package com.challenge.meli.product.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.meli.R
import com.challenge.meli.databinding.ListItemProductBinding
import com.challenge.meli.product.model.Product
import com.challenge.meli.utils.NumberHelper

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ListItemProductBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(item: Product, context: Context) {
        Glide.with(context).load(item.thumbnail).centerInside().into(binding.imageViewProduct)
        binding.textViewTitle.text = item.title
        binding.textViewPrice.text = "$ ${NumberHelper().parseAmountToCOP(item.price)}"
        if (item.installments != null) binding.textViewInstallments.text =
            "${item.installments!!.quantity}x $${NumberHelper().parseAmountToCOP(item.installments!!.amount)}"
        val lblAvailable: String = context.getString(R.string.lbl_available)
        binding.textViewQuantity.text = "${item.availableQuantity} $lblAvailable"
    }
}