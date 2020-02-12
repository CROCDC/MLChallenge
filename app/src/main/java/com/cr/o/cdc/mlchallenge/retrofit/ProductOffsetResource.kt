package com.cr.o.cdc.mlchallenge.retrofit

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.cr.o.cdc.mlchallenge.db.model.ProductOffset

data class ProductOffsetResource(
    val status: LiveData<StatusResponse>,
    val data: LiveData<PagedList<ProductOffset>>,
    val refresh: () -> Unit
) {
    fun refresh() {
        refresh.invoke()
    }
}
