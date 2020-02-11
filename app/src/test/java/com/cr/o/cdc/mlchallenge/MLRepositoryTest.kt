package com.cr.o.cdc.mlchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cr.o.cdc.mlchallenge.retrofit.MLRepository
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test


class MLRepositoryTest {

    private val mlRepository = MLRepository()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun search() {
        val data = getValue(mlRepository.search("Motorola")).data
        assertTrue(data?.products != null)
        print(data?.products)
    }

    @Test
    fun item() {
        val data = getValue(mlRepository.item("MLA825164585")).data
        assertTrue(data != null)
        print(data)
    }
}