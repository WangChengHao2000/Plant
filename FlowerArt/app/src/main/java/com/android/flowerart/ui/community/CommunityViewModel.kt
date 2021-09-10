package com.android.flowerart.ui.community

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.flowerart.logic.Repository
import com.android.flowerart.logic.bean.Record

class CommunityViewModel : ViewModel() {

    private val communityLiveData = MutableLiveData<Int>()

    val communityResultLiveData = Transformations.switchMap(communityLiveData) { _ ->
        Repository.selectAllRecord()
    }

    fun selectCommunity() {
        communityLiveData.value = null
    }

    val communityList = ArrayList<Record>()

}