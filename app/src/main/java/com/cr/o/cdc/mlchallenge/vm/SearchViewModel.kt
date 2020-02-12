package com.cr.o.cdc.mlchallenge.vm

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.cr.o.cdc.mlchallenge.db.model.ProductOffset
import com.cr.o.cdc.mlchallenge.retrofit.MLRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: MLRepository) : ViewModel() {

    private val search = MutableLiveData<String>()

    private val productResource = search.map {
        repository.search(it)
    }

    val products: LiveData<PagedList<ProductOffset>> = productResource.switchMap {
        it.data
    }

    val loading = productResource.switchMap {
        it.status
    }

    fun setSearch(search: String?) {
        this.search.value = search
    }

    fun refresh() {
        productResource.value?.refresh()
    }
}