package com.challenge.meli.product.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("query") var message: String,
    @SerializedName("results") var results: ArrayList<Product>
)