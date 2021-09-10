package com.android.flowerart.utils

import okhttp3.*
import java.io.File
import kotlin.concurrent.thread


object PicUtil {

    fun uploadPic(file: File): String {
        var address = ""
        thread {
            val client = OkHttpClient()
            val url = "http://47.100.163.39:8802/uploadPic"
            val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            val body = RequestBody.create(MediaType.parse("image/*"), file)
            requestBody.addFormDataPart("file", file.name, body);

            val request = Request.Builder()
                .url(url).post(requestBody.build())
                .build()

            address = client.newCall(request).execute().body()?.string().toString()
        }.join()
        return address
    }

}