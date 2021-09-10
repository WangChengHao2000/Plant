package com.android.flowerart.utils

import com.android.flowerart.FlowerApplication
import com.android.flowerart.logic.bean.InfoResult
import com.android.flowerart.logic.bean.PlantInfo
import com.android.flowerart.logic.bean.PlantType
import com.google.gson.Gson
import okhttp3.*
import kotlin.concurrent.thread


object APIShopUtil {

    fun getPlantID(plantName: String): Int {
        var plantID = 0
        thread {
            val client = OkHttpClient()
            val url = FlowerApplication.APIShop_url + "/queryPlantListByKeyword"
            val formBody = FormBody.Builder()
                .add("apiKey", FlowerApplication.api_key)
                .add("page", "1")
                .add("pageSize", "1")
                .add("keyword", plantName)
                .build()
            val request = Request.Builder()
                .url(url).post(formBody)
                .build()

            val jsonString = client.newCall(request).execute().body()?.string()
            val gson = Gson()
            val result = gson.fromJson(jsonString, PlantType::class.java)
            if (result.result.plantList.size == 1) {
                plantID = result.result.plantList[0].plantID
            }
        }.join()
        return plantID
    }

    fun getPlantInfo(plantID: Int): InfoResult? {
        var infoResult: InfoResult? = null
        thread {
            val client = OkHttpClient()
            val url = FlowerApplication.APIShop_url + "/queryPlantInfo"
            val formBody = FormBody.Builder()
                .add("apiKey", FlowerApplication.api_key)
                .add("plantID", plantID.toString())
                .build()
            val request = Request.Builder()
                .url(url).post(formBody)
                .build()

            val jsonString = client.newCall(request).execute().body()?.string()
            val gson = Gson()
            val result = gson.fromJson(jsonString, PlantInfo::class.java)
            if (result.result != null)
                infoResult = result.result
        }.join()
        return infoResult
    }
}