package com.challenge.meli.data.model

import com.google.gson.annotations.SerializedName

class Attribute {
    var id: String = ""

    @SerializedName("value_name")
    var valeName: String = ""
}

internal enum class AttributeType {
    ITEM_CONDITION, SINGLE_OWNER, HAS_AIR_CONDITIONING;

    override fun toString(): String {
        return when (this) {
            ITEM_CONDITION -> "ITEM_CONDITION"
            SINGLE_OWNER -> "SINGLE_OWNER"
            HAS_AIR_CONDITIONING -> "HAS_AIR_CONDITIONING"
        }
    }
}