package com.cr.o.cdc.mlchallenge.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitResource
import com.cr.o.cdc.mlchallenge.retrofit.StatusResponse

class LoadingHandler<T> : Observer<RetrofitResource<T>> {

    private var source: LiveData<RetrofitResource<T>>? = null
    private val status = MutableLiveData<Boolean>()

    fun setSource(source: LiveData<RetrofitResource<T>>) {
        this.source = source
        source.observeForever(this)
    }

    fun getStatus(): LiveData<Boolean> = status


    override fun onChanged(t: RetrofitResource<T>?) {
        if (t != null) {
            status.value = t.status == StatusResponse.LOADING

            if (t.status != StatusResponse.LOADING && source != null) {
                source?.removeObserver(this)
                source = null
            }
        }
    }

}