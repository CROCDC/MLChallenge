package com.cr.o.cdc.mlchallenge.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cr.o.cdc.mlchallenge.db.model.InfoSearchProduct
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.db.model.ProductOffset
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

    @Query("DELETE FROM info_search_product WHERE search == :search")
    fun delete(search: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveOffSet(items: List<InfoSearchProduct>)

    @Query(
        "SELECT * FROM info_search_product LEFT JOIN product ON product_id == id WHERE search == :search"
    )
    fun loadPaged(search: String): DataSource.Factory<Int, ProductOffset>
}