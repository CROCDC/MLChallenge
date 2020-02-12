package com.cr.o.cdc.mlchallenge.retrofit

import androidx.lifecycle.LiveData
import androidx.paging.toLiveData
import com.cr.o.cdc.mlchallenge.db.MLChallengeDB
import com.cr.o.cdc.mlchallenge.db.model.Product
import retrofit2.Retrofit
import javax.inject.Inject


class MLRepository @Inject constructor(
    val db: MLChallengeDB,
    val retrofit: Retrofit,
    val appExecutors: AppExecutors
) {


    private val mlApi: MLApi = retrofit.create(MLApi::class.java)

    fun search(search: String): ProductOffsetResource {
        val boundaryCallback = ProductOffsetBoundaryCallback(
            search,
            retrofit,
            appExecutors.networkIO(),
            db.productsDao()
        )
        return ProductOffsetResource(
            boundaryCallback.networkStatus,
            db.productsDao().loadPaged(search).toLiveData(
                10, boundaryCallback = boundaryCallback
            )
        ) {
            appExecutors.diskIO().execute {
                db.productsDao().delete(search)
            }
        }

    }

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