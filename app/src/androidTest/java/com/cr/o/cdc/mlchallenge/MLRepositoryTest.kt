package com.cr.o.cdc.mlchallenge

import com.cr.o.cdc.mlchallenge.di.AppModule
import com.cr.o.cdc.mlchallenge.retrofit.MLRepository
import org.junit.Test

class MLRepositoryTest {

    val mlRepository= MLRepository(
        AppModule()
    )

    @Test
    fun saveProductsInSearchAndShowProductInDetail() {

    }
}