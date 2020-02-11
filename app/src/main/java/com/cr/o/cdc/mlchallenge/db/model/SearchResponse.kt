package com.cr.o.cdc.mlchallenge.db.model

import com.google.gson.annotations.SerializedName

class SearchResponse(
    @SerializedName("results")
    val products: List<Product>
)