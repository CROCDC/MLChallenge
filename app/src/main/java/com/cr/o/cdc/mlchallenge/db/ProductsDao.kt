package com.cr.o.cdc.mlchallenge.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.db.model.SearchResponse

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(list: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(product: SearchResponse)

    @Query("SELECT * FROM SearchResponse WHERE search =:search")
    fun load(search: String): LiveData<SearchResponse?>

    @Query("SELECT * FROM Product WHERE id =:id")
    fun loadProduct(id: String): LiveData<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(product: Product)
}