package com.cr.o.cdc.mlchallenge.db.model

import com.google.gson.annotations.SerializedName

data class Attribute(
    val id: String,
    val name: String,
    @SerializedName("value_name")
    val valueName: String?
) {

    companion object {

        const val ID_BRAND = "BRAND"
    }
}