package com.cr.o.cdc.mlchallenge.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.retrofit.MLRepository
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitResource
import com.cr.o.cdc.mlchallenge.utils.LoadingHandler
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: MLRepository) : ViewModel() {

    private val search = MutableLiveData<String>()

    private val handler = LoadingHandler()

    val loading
        get() = handler.getStatus()

    val products: LiveData<RetrofitResource<List<Product>>> =
        Transformations.switchMap(search) { search ->
            repository.search(search).also { r ->
                handler.setSource(r)
            }
        }

    fun setSearch(search: String?) {
        this.search.value = search
    }

    fun refresh() {
        val search = search.value
        if (search != null) {
            handler.setSource(repository.search(search))
        } else {
            handler.setSource<Any>(null)
        }
    }
}