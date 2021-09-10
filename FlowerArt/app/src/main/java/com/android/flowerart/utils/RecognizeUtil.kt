package com.android.flowerart.utils

import com.android.flowerart.FlowerApplication
import com.android.flowerart.logic.bean.Results
import com.google.gson.Gson
import java.net.URLEncoder
import kotlin.concurrent.thread

object RecognizeUtil {

    fun recognize(filePath: String): Results {

        val imgData = FileUtil.readFileByBytes(filePath)
        val imgStr = Base64Util.encode(imgData)
        val imgParam: String = URLEncoder.encode(imgStr, "UTF-8")
        val param = "image=$imgParam"

        var result: String? = null
        thread {
            result =
                HttpUtil.post(FlowerApplication.url, FlowerApplication.accessToken, param)
        }.join()

        val gson = Gson()

        return gson.fromJson(result, Results::class.java)
    }

}