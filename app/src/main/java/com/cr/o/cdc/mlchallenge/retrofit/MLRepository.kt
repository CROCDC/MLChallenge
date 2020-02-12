package com.cr.o.cdc.mlchallenge.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.cr.o.cdc.mlchallenge.db.MLChallengeDB
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.db.model.SearchResponse
import retrofit2.Retrofit
import javax.inject.Inject


class MLRepository @Inject constructor(
    val db: MLChallengeDB,
    val retrofit: Retrofit,
    val appExecutors: AppExecutors
) {


    private val mlApi: MLApi = retrofit.create(MLApi::class.java)

    fun search(search: String): LiveData<RetrofitResource<List<Product>>> =
        object : NetworkBoundResource<List<Product>, SearchResponse>(
            appExecutors
        ) {
            override fun saveCallResult(item: SearchResponse?) {
                item?.let {
                    db.productsDao().save(it)
                    db.productsDao().saveAll(it.products)
                }
            }

            override fun shouldFetch(data: List<Product>?): Boolean = true

            override fun loadFromDb(): LiveData<List<Product>> =
                db.productsDao().load(search).map {
                    it?.products ?: listOf()
                }

            override fun createCall(): LiveData<RetrofitResponse<SearchResponse>> =
                mlApi.search(search)

        }.asLiveData()

    fun item(id: String): LiveData<RetrofitResource<Product>> =
        object : NetworkBoundResource<Product, Product>(appExecutors) {
            override fun saveCallResult(item: Product?) {
                item?.let {
                    db.productsDao().save(it)
                }
            }

            override fun shouldFetch(data: Product?): Boolean = true

            override fun loadFromDb(): LiveData<Product> = db.productsDao().loadProduct(id)

            override fun createCall(): LiveData<RetrofitResponse<Product>> = mlApi.item(id)

        }.asLiveData()
}