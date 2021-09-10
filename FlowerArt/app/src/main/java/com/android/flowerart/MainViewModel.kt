package com.android.flowerart

import androidx.lifecycle.ViewModel
import com.android.flowerart.logic.Repository

class MainViewModel : ViewModel() {

    fun getSaveUser() = Repository.getSaveUser()
    fun isSaveUser() = Repository.isSaveUser()

}