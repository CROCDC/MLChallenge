package com.cr.o.cdc.mlchallenge.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.retrofit.MLRepository
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitResource
import com.cr.o.cdc.mlchallenge.utils.LoadingHandler
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(private val repository: MLRepository) :
    ViewModel() {

    private val handler = LoadingHandler()

    val loading
        get() = handler.getStatus()

    private val productId = MutableLiveData<String>()

    val product: LiveData<RetrofitResource<Product>> = productId.switchMap { id ->
        repository.item(id).also {
            handler.setSource(it)
        }
    }

    fun setProductId(id: String) {
        productId.value = id
    }

    fun refresh() {
        val search = productId.value
        if (search != null) {
            handler.setSource(repository.item(search))
        } else {
            handler.setSource<Any>(null)
        }
    }
}