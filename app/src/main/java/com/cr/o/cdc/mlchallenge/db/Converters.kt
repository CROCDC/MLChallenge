package com.cr.o.cdc.mlchallenge.db

import androidx.room.TypeConverter
import com.cr.o.cdc.mlchallenge.db.model.Attribute
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun restoreAttributes(attributes: String): List<Attribute> =
        GsonBuilder().create().fromJson(attributes, object : TypeToken<List<Attribute>>() {}.type)

    @TypeConverter
    fun saveAttributes(list: List<Attribute>): String = Gson().toJson(list)

    @TypeConverter
    fun restoreProducts(products: String): List<Product> =
        GsonBuilder().create().fromJson(products, object : TypeToken<List<Product>>() {}.type)

    @TypeConverter
    fun saveProducts(list: List<Product>): String = Gson().toJson(list)

}