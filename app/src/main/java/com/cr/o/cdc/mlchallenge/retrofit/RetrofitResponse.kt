package com.cr.o.cdc.mlchallenge.retrofit

import retrofit2.Response

sealed class RetrofitResponse<T> {
    companion object {
        fun <T> create(error: Throwable): RetrofitErrorResponse<T> = RetrofitErrorResponse()


        fun <T> create(response: Response<T>): RetrofitResponse<T> {
            val body = response.body()
            return if (body == null || response.code() == 204) {
                RetrofitErrorResponse()
            } else {
                RetrofitSuccessResponse(body)
            }
        }
    }
}


data class RetrofitSuccessResponse<T>(
    val data: T?
) : RetrofitResponse<T>()

class RetrofitErrorResponse<T> : RetrofitResponse<T>()