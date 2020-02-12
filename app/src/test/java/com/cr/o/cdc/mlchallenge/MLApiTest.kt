package com.cr.o.cdc.mlchallenge

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cr.o.cdc.mlchallenge.db.model.SearchResponse
import com.cr.o.cdc.mlchallenge.di.AppModule
import com.cr.o.cdc.mlchallenge.retrofit.MLApi
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitSuccessResponse
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        mlApi.search("Motorola", 0)
            .enqueue(object : Callback<SearchResponse> {

                override fun onFailure(
                    call: Call<SearchResponse>,
                    t: Throwable
                ) {
                    throw Exception(t)
                }

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    assertTrue(response.body() != null)
                    print((response as RetrofitSuccessResponse<SearchResponse>).data)
                }
            })

    }

    @Test
    fun item() {
        val response = getValue(mlApi.item("MLA825164585")) as? RetrofitSuccessResponse
        assertTrue(response?.data != null)
        print(response?.data)
    }
}