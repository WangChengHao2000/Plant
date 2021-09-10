package com.android.flowerart.ui.person

import androidx.lifecycle.ViewModel
import com.android.flowerart.logic.Repository

class PersonViewModel:ViewModel() {

    fun deleteSaveUser() = Repository.deleteSaveUser()

}