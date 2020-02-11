package com.cr.o.cdc.mlchallenge

import androidx.lifecycle.LiveData
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitResource
import com.cr.o.cdc.mlchallenge.retrofit.StatusResponse
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun <T> getValue(liveData: LiveData<RetrofitResource<T>>): RetrofitResource<T> {
    var response = RetrofitResource<T>(null, StatusResponse.LOADING)
    val latch = CountDownLatch(1)
    liveData.observeForever {
        when (it?.status) {
            StatusResponse.SUCCESS -> {
                response = it
                latch.countDown()
            }
            StatusResponse.ERROR -> {
                throw Exception("Error in request")
            }
        }
    }
    latch.await(1000, TimeUnit.SECONDS)
    return response
}