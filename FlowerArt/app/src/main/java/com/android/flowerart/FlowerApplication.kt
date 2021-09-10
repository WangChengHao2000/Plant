package com.android.flowerart

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import java.io.File

class FlowerApplication : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant"
        const val accessToken =
            "24.9ae5aeef9fd0706c77cdf34655752351.2592000.1633603815.282335-24492982"

        const val APIShop_url = "http://api.apishop.net/common/plantFamily"
        const val api_key = "hWpHts9842a48c84cc48a420b89f59c615e625c143364cf"

        var account: Int = 0

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}