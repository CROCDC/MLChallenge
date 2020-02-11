package com.cr.o.cdc.mlchallenge.retrofit

import androidx.lifecycle.LiveData
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.db.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MLApi {

    @GET("/sites/MLA/search")
    fun search(@Query(value = "q") search: String): LiveData<RetrofitResponse<SearchResponse>>

    @GET("/items/{id}")
    fun item(@Path("id") id: String): LiveData<RetrofitResponse<Product>>
}