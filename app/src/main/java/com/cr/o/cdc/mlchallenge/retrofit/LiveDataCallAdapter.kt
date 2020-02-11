package com.cr.o.cdc.mlchallenge.retrofit

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

//https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/util/LiveDataCallAdapter.kt
class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<RetrofitResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<RetrofitResponse<R>> {
        return object : LiveData<RetrofitResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(RetrofitResponse.create(response))
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(RetrofitResponse.create(throwable))
                        }
                    })
                }
            }
        }
    }
}