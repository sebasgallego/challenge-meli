package com.challenge.meli.ui.product.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Product: Serializable {
    var id: String = ""
    var title: String = ""
    var thumbnail: String = ""
    var price: Double = 0.0
    @SerializedName("available_quantity")
    var availableQuantity: Int = 0
    var installments : Installment? = null
    //var attributes : ArrayList<Attribute>? = null
}
