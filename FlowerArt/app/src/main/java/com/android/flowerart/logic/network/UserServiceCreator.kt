package com.android.flowerart.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserServiceCreator {

    private const val BASE_URL = "http://47.100.163.39:8802/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}