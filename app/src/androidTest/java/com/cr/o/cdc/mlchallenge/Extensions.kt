package com.cr.o.cdc.mlchallenge

import androidx.lifecycle.LiveData
import com.cr.o.cdc.mlchallenge.retrofit.RetrofitResource
import com.cr.o.cdc.mlchallenge.retrofit.StatusResponse
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

fun <T> getValue(liveData: LiveData<RetrofitResource<T>>): RetrofitResource<T>? {
    var response = RetrofitResource.loading<T>()
    val latch = CountDownLatch(1)
    liveData.observeForever {
        when {
            it.status == StatusResponse.LOADING && it.data != null -> {
                if (it.data is List<*>) {
                    if ((it.data as? List<*>)?.isNotEmpty() == true) {
                        response = it
                        latch.countDown()
                    }
                } else {
                    response = it
                    latch.countDown()
                }
            }
            it.status == StatusResponse.SUCCESS && it.data != null -> {
                if (it.data is List<*>) {
                    if ((it.data as? List<*>)?.isNotEmpty() == true) {
                        response = it
                        latch.countDown()
                    }
                } else {
                    response = it
                    latch.countDown()
                }
            }
            it.status == StatusResponse.ERROR -> throw Exception("Error")
        }
    }
    latch.await(20, TimeUnit.SECONDS)
    return response
}

fun <T> KClass<*>.makeRandomInstance(
    parameters: List<Parameter> = listOf()
): T = try {
    when (this) {
        List::class -> listOf<Any>()
        Boolean::class -> true
        Int::class -> Random.nextInt()
        Long::class -> Random.nextLong()
        Double::class -> Random.nextDouble(100.0, 200.0)
        Float::class -> Random.nextFloat()
        Char::class -> makeRandomString()[0]
        String::class -> makeRandomString()
        else -> (this.constructors as ArrayList<KFunction<Any>>)[0].let { kFunction ->
            kFunction.call(
                *kFunction.parameters.map { kParameter ->
                    val key = parameters.find { parameter ->
                        parameter.name == kParameter.name && parameter.place.qualifiedName == kParameter.toString().split(
                            ":"
                        )[1].replace(" ", "")
                    }
                    return@map key?.value
                        ?: (kParameter.type.classifier as KClass<*>).makeRandomInstance(parameters)
                }.toTypedArray()
            )
        }
    } as T
} catch (e: Exception) {
    println(this)
    println(e)
    throw IllegalStateException("problem with class $this")
}

data class Parameter(
    val name: String,
    val place: KClass<*>,
    val value: Any
)

private fun makeRandomString(): String {
    val charPool = ('a'..'z') + ('A'..'Z')
    return (1..10)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}