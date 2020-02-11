package com.cr.o.cdc.mlchallenge.db.model

import com.google.gson.annotations.SerializedName

data class Shipping(
    @SerializedName("free_shipping")
    val free: Boolean
)
