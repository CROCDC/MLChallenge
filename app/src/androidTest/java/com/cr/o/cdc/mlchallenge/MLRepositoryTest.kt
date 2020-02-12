package com.cr.o.cdc.mlchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.cr.o.cdc.mlchallenge.db.MLChallengeDB
import com.cr.o.cdc.mlchallenge.db.model.Product
import com.cr.o.cdc.mlchallenge.db.model.SearchResponse
import com.cr.o.cdc.mlchallenge.di.AppModule
import com.cr.o.cdc.mlchallenge.retrofit.MLApi
import com.cr.o.cdc.mlchallenge.retrofit.MLRepository
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitSuccessResponse
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit

class MLRepositoryTest {

    private val app =
        (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MLChallengeApp).also {
            it.deleteDatabase(MLChallengeDB.DATABASE_NAME)
        }

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val product: Product = Product::class.makeRandomInstance(
        listOf(
            Parameter(
                "id",
                Product::class,
                "1"
            )
        )
    )

    private val mutableLiveData = MutableLiveData<RetrofitSuccessResponse<SearchResponse>>()

    private val mlRepository = MLRepository(
        Room.databaseBuilder(app, MLChallengeDB::class.java, MLChallengeDB.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build(),
        mockk<Retrofit>(relaxed = true).apply {
            every { create(MLApi::class.java) } returns mockk<MLApi>(relaxed = true).apply {
                every { item("1") } returns MutableLiveData(
                    RetrofitSuccessResponse(
                        product.copy(
                            title = "title for network"
                        )
                    )
                )
                every { search("motorola") } returns MutableLiveData(
                    RetrofitSuccessResponse(
                        SearchResponse::class.makeRandomInstance(
                            listOf(
                                Parameter(
                                    "search",
                                    SearchResponse::class,
                                    "motorola"
                                ),
                                Parameter(
                                    "products",
                                    SearchResponse::class,
                                    listOf<Product>(
                                        product,
                                        Product::class.makeRandomInstance(
                                            listOf(
                                                Parameter(
                                                    "id",
                                                    Product::class,
                                                    "2"
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            }
        },
        AppModule().provideAppExecutors()
    )

    @Test
    fun search() {
        val resource = getValue(mlRepository.search("motorola"))
        assertTrue(resource?.data?.get(0)?.id == "1")
    }

    @Test
    fun item() {
        val resource = getValue(mlRepository.item("1"))
        assertTrue(resource?.data?.id == "1")
    }

    @Test
    fun loadItemFromSearchSave() {
        getValue(mlRepository.search("motorola"))
        val resource = getValue(mlRepository.item("1"))
        assertTrue(resource?.data?.id == "1")
        assertTrue(resource?.data?.title != "title for network")
    }
}