package com.cr.o.cdc.mlchallenge.retrofit

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.cr.o.cdc.mlchallenge.db.ProductsDao
import com.cr.o.cdc.mlchallenge.db.model.OffsetProduct
import com.cr.o.cdc.mlchallenge.db.model.ProductOffset
import retrofit2.Retrofit
import java.util.concurrent.Executor

class ProductOffsetBoundaryCallback(
    private val search: String,
    retrofit: Retrofit,
    private val networkIO: Executor,
    private val dao: ProductsDao

) : PagedList.BoundaryCallback<ProductOffset>() {

    private val mlApi = retrofit.create(MLApi::class.java)
    val networkStatus = MutableLiveData<StatusResponse>()

    @MainThread
    override fun onZeroItemsLoaded() {
        fetch(0)
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: ProductOffset) {
        fetch(itemAtEnd.offset + 10)
    }

    private fun fetch(offset: Int) {
        if (networkStatus.value == StatusResponse.LOADING) {
            return
        }

        networkStatus.value = StatusResponse.LOADING

        networkIO.execute {
            val response = mlApi.search(search, offset).execute().body()

            if (response != null) {
                dao.saveAll(response.products)
                dao.saveOffSet(response.products.map { OffsetProduct(it.id, offset, search) })

                networkStatus.postValue(StatusResponse.SUCCESS)
            } else {
                networkStatus.postValue(StatusResponse.ERROR)
            }
        }
    }

}