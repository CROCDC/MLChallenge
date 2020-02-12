package com.cr.o.cdc.mlchallenge.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class SearchResponse(
    @PrimaryKey
    @SerializedName("query")
    val search: String,
    @Embedded val paging: Paging,
    @SerializedName("results")
    val products: List<Product>
)