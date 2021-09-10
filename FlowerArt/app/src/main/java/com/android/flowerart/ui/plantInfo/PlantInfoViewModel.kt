package com.android.flowerart.ui.plantInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.flowerart.logic.Repository
import com.android.flowerart.logic.bean.Record
import java.io.File

class PlantInfoViewModel : ViewModel() {

    private val recordLiveData = MutableLiveData<Record>()

    val recordResultLiveData = Transformations.switchMap(recordLiveData) { record ->
        Repository.insertRecord(record)
    }

    fun insertRecord(record: Record) {
        recordLiveData.value = record
    }

    fun uploadPic(file: File): String = Repository.uploadPic(file)
}