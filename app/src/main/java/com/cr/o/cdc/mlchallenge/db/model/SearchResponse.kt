package com.cr.o.cdc.mlchallenge.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class SearchResponse(
    @SerializedName("query")
    val search: String,
    @SerializedName("results")
    val products: List<Product>
)