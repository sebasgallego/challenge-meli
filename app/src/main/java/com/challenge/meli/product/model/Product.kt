package com.challenge.meli.product.model

import com.google.gson.annotations.SerializedName

class Product {
    var id: String = ""
    var title: String = ""
    var thumbnail: String = ""
    var price: Double = 0.0
    @SerializedName("available_quantity")
    var availableQuantity: Int = 0
    var installments : Installments? = null
}
