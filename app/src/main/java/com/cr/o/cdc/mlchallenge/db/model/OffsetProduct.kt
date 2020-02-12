package com.cr.o.cdc.mlchallenge.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "offset_product", primaryKeys = ["product_id", "search"])
data class OffsetProduct(
    @ColumnInfo(name = "product_id") val id: String,
    val offset: Int,
    @ColumnInfo(name = "search") val search: String
)

data class ProductOffset(@Embedded val product: Product, val offset: Int)