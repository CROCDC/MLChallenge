package com.cr.o.cdc.mlchallenge.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @PrimaryKey
    val id: String,
    @Embedded val shipping: Shipping,
    val thumbnail: String,
    val title: String,
    val price: Double,
    @SerializedName("sold_quantity")
    val soldQuantity: Int,
    val attributes: List<Attribute>
) {
    fun getAttributesFiltered(): List<Attribute> = attributes.filter {
        it.valueName != null
    }
}