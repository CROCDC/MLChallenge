package com.cr.o.cdc.mlchallenge

import androidx.lifecycle.LiveData
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitErrorResponse
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitSuccessResponse
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitResponse
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun <T> getValue(liveData: LiveData<RetrofitResponse<T>>): RetrofitResponse<T>? {
    var response: RetrofitResponse<T>? = null
    val latch = CountDownLatch(1)
    liveData.observeForever {
        when (it) {
            is RetrofitSuccessResponse -> {
                response = it
                latch.countDown()
            }
            is RetrofitErrorResponse -> throw Exception("Error in request")
        }
    }
    latch.await(10, TimeUnit.SECONDS)
    return response
}

