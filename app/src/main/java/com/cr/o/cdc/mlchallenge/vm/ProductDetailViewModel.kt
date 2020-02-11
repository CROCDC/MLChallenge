package com.cr.o.cdc.mlchallenge.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.retrofit.MLRepository
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitResource
import com.cr.o.cdc.mlchallenge.utils.LoadingHandler
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(private val repository: MLRepository) :
    ViewModel() {

    private val handler = LoadingHandler<Product>()

    val loading
        get() = handler.getStatus()


    fun item(id: String): LiveData<RetrofitResource<Product>> = repository.item(id).also {
        handler.setSource(it)
    }
}