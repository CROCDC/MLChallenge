package com.cr.o.cdc.mlchallenge.retrofit

data class RetrofitResource<T>(val data: T?, val status: StatusResponse) {


    companion object {

        fun <T> error() = RetrofitResource<T>(null, StatusResponse.ERROR)
        fun <T> loading(data: T? = null) = RetrofitResource(data, StatusResponse.LOADING)
        fun <T> success(data: T?) = RetrofitResource(data, StatusResponse.SUCCESS)
    }
}