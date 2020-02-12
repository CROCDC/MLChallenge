package com.cr.o.cdc.mlchallenge.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitResource
import com.cr.o.cdc.mlchallenge.retrofit.StatusResponse

class LoadingHandler : Observer<RetrofitResource<*>> {

    private var source: LiveData<RetrofitResource<*>>? = null
    private val status = MutableLiveData<Boolean>()

    fun <T> setSource(source: LiveData<RetrofitResource<T>>?) {
        if (source != null) {
            @Suppress("UNCHECKED_CAST")
            this.source = source as LiveData<RetrofitResource<*>>
            source.observeForever(this)
        } else {
            this.source = null
            source?.removeObserver(this)
            status.value = false
        }

    }

    fun getStatus(): LiveData<Boolean> = status

    override fun onChanged(t: RetrofitResource<*>?) {
        if (t != null) {
            status.value = t.status == StatusResponse.LOADING

            if (t.status != StatusResponse.LOADING && source != null) {
                source?.removeObserver(this)
                source = null
            }
        }
    }

}