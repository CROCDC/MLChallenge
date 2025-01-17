package com.cr.o.cdc.mlchallenge.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cr.o.cdc.mlchallenge.db.model.InfoSearchProduct
import com.cr.o.cdc.mlchallenge.db.model.Product

@Database(entities = [Product::class, InfoSearchProduct::class], version = 1)
@TypeConverters(Converters::class)
abstract class MLChallengeDB : RoomDatabase() {

    abstract fun productsDao(): ProductsDao

    companion object {
        const val DATABASE_NAME = "ml_challenge_db"
    }

}