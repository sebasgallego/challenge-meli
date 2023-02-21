package com.challenge.meli.data.model

import com.google.gson.annotations.SerializedName

class Attribute {

    var id: String = ""

    @SerializedName("value_name")
    var valeName: String = ""

}

internal enum class AttributeType {
    BRAND,
    LINE,
    MODEL,
    SUB_MODEL,
    ITEM_CONDITION;

    override fun toString(): String {
        return when (this) {
            LINE -> "LINE"
            BRAND -> "BRAND"
            MODEL -> "MODEL"
            SUB_MODEL -> "SUBMODEL"
            ITEM_CONDITION -> "ITEM_CONDITION"
        }
    }
}