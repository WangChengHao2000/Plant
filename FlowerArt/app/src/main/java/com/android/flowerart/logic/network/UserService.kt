package com.android.flowerart.logic.network

import com.android.flowerart.logic.bean.Record
import com.android.flowerart.logic.bean.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("register")
    fun register(@Body user: User): Call<User>

    @POST("login")
    fun login(@Body user: User): Call<User>

    @POST("insertRecord")
    fun insertRecord(@Body record: Record): Call<Record>

    @POST("selectRecord")
    fun selectRecord(@Body account: Int): Call<List<Record>>

    @POST("selectAllRecord")
    fun selectAllRecord(): Call<List<Record>>
}