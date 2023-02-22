package com.challenge.meli.data.model

import com.google.gson.annotations.SerializedName


class Product {

    var id: String = ""
    var title: String = ""
    var thumbnail: String = ""
    var price: Double = 0.0
    @SerializedName("available_quantity")
    var availableQuantity: Int = 0
    @SerializedName("sold_quantity")
    var soldQuantity: Int = 0
    var installments: Installment? = null
    var attributes: ArrayList<Attribute>? = null

    fun getItemAttributes(type: String): String {
        var value = ""
        for (item: Attribute in attributes!!) {
              if (type == item.id){
                  value = item.valeName
                  break
              }
        }
        return value
    }

}
