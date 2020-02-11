package com.cr.o.cdc.mlchallenge.retrofit

import retrofit2.Response

sealed class RetrofitResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> = ApiErrorResponse()


        fun <T> create(response: Response<T>): RetrofitResponse<T> {
            val body = response.body()
            return if (body == null || response.code() == 204) {
                ApiErrorResponse()
            } else {
                ApiSuccessResponse(body = body)
            }
        }
    }
}


data class ApiSuccessResponse<T>(
    val body: T?
) : RetrofitResponse<T>()

class ApiErrorResponse<T> : RetrofitResponse<T>()