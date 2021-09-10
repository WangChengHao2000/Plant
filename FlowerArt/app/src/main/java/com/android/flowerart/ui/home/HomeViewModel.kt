package com.android.flowerart.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.flowerart.logic.Repository
import com.android.flowerart.logic.bean.Flower

class HomeViewModel : ViewModel() {

    private val recognizeLiveData = MutableLiveData<String>()

    val recognizeResultLiveData = Transformations.switchMap(recognizeLiveData) { filePath ->
        Repository.recognize(filePath)
    }

    fun recognize(filePath: String) {
        recognizeLiveData.value = filePath
    }

    val resultList = ArrayList<Flower>()

}