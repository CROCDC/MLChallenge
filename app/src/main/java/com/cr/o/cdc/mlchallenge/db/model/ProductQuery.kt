package com.cr.o.cdc.mlchallenge.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

@Entity(primaryKeys = ["search"])
data class ProductQuery(
    @ColumnInfo(name = "search") val search: String,
    @Embedded val product: Product
)