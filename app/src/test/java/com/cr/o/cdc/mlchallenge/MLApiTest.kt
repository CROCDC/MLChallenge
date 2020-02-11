package com.cr.o.cdc.mlchallenge

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cr.o.cdc.mlchallenge.di.AppModule
import com.cr.o.cdc.mlchallenge.retrofit.MLApi
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitSuccessResponse
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test


class MLApiTest {

    private val mlApi = AppModule().provideRetrofit(mockk<MLChallengeApp>().apply {
        every { resources } returns mockk<Resources>().apply {
            every { getString(R.string.api_ml_url) } returns "https://api.mercadolibre.com"
        }
    }).create(MLApi::class.java)

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun search() {
        val response = getValue(mlApi.search("Motorola")) as? RetrofitSuccessResponse
        assertTrue(response?.data != null)
        print(response?.data)
    }

    @Test
    fun item() {
        val response = getValue(mlApi.item("MLA825164585")) as? RetrofitSuccessResponse
        assertTrue(response?.data != null)
        print(response?.data)
    }
}