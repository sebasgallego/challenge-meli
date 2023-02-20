package com.challenge.meli.ui.product.data.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("results") var results: ArrayList<Product>
)