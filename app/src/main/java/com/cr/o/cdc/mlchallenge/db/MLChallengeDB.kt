package com.cr.o.cdc.mlchallenge.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.db.model.ProductQuery

@Database(entities = [ProductQuery::class, Product::class], version = 1)
@TypeConverters(Converters::class)
abstract class MLChallengeDB : RoomDatabase() {

    abstract fun productsInfoDao(): ProductsInfoDao

    companion object {
        val DATABASE_NAME = "ml_challenge_db"
    }

}