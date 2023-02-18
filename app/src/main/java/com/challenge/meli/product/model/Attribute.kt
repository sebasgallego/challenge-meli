package com.challenge.meli.product.model

import com.google.gson.annotations.SerializedName

class Attribute{
    var id: String = "0"
    @SerializedName("value_name")
    var valeName:String = "0"
}