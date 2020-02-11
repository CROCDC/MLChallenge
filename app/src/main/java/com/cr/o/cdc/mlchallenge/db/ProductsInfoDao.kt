package com.cr.o.cdc.mlchallenge.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.db.model.ProductQuery

@Dao
interface ProductsInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(list: List<ProductQuery>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(product: Product?)

    @Query("SELECT * FROM ProductQuery WHERE search =:search")
    fun load(search: String): LiveData<List<Product>>

    @Query("SELECT * FROM ProductQuery WHERE id =:id")
    fun loadProduct(id: String): LiveData<Product>

}