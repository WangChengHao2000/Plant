package com.android.flowerart.ui.record

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.flowerart.logic.Repository
import com.android.flowerart.logic.bean.Record

class RecordViewModel : ViewModel() {

    private val recordLiveData = MutableLiveData<Int>()

    val recordResultLiveData = Transformations.switchMap(recordLiveData) { account ->
        Repository.selectRecord(account)
    }

    fun selectRecord(account: Int) {
        recordLiveData.value = account
    }

    val recordList = ArrayList<Record>()

}