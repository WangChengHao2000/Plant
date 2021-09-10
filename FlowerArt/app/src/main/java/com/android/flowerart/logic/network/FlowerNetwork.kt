package com.android.flowerart.logic.network

import com.android.flowerart.logic.bean.Record
import com.android.flowerart.logic.bean.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object FlowerNetwork {

    private val userService = UserServiceCreator.create(UserService::class.java)

    suspend fun register(user: User) = userService.register(user).await()

    suspend fun login(user: User) = userService.login(user).await()

    suspend fun insertRecord(record: Record) = userService.insertRecord(record).await()

    suspend fun selectRecord(account: Int) = userService.selectRecord(account).await()

    suspend fun selectAllRecord() = userService.selectAllRecord().await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}