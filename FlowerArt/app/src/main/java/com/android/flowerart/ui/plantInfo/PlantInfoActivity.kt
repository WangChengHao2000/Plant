package com.android.flowerart.ui.plantInfo

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.flowerart.FlowerApplication
import com.android.flowerart.R
import com.android.flowerart.logic.bean.InfoResult
import com.android.flowerart.logic.bean.Record
import com.android.flowerart.utils.APIShopUtil
import java.io.File
import java.text.SimpleDateFormat


class PlantInfoActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(PlantInfoViewModel::class.java) }

    private var plantInfo: InfoResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_info)

        val plantID = intent.getIntExtra("plantID", 0)
        findViewById<TextView>(R.id.plantID_info).text = plantID.toString()

        Log.d("plantID", plantID.toString())

        if (plantID != 0) {
            val result = APIShopUtil.getPlantInfo(plantID)
            if (result != null) {
                plantInfo = result
                findViewById<TextView>(R.id.plantAlias_info).text = result.alias
                findViewById<TextView>(R.id.plantClassName_info).text = result.className
                findViewById<TextView>(R.id.plantFeature_info).text = result.feature
            } else {
                findViewById<Button>(R.id.makeRecord).visibility = GONE
            }
        } else {
            findViewById<Button>(R.id.makeRecord).visibility = GONE
        }

        findViewById<Button>(R.id.makeRecord).setOnClickListener {
            if (FlowerApplication.account != 0) {
                val file = File(externalCacheDir, "result.jpg")
                val address = viewModel.uploadPic(file)
                Log.d("Address", address)
                val record = Record(
                    0,
                    FlowerApplication.account,
                    plantInfo!!.name,
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis()),
                    address
                )
                Log.d("Address", record.address)
                viewModel.insertRecord(record)
            } else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.recordResultLiveData.observe(this, { result ->
            val record = result.getOrNull()
            if (record != null) {
                Toast.makeText(this, "打卡成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "打卡失败", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}