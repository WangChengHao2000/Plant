package com.android.flowerart.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.flowerart.logic.Repository
import com.android.flowerart.logic.bean.SaveUser
import com.android.flowerart.logic.bean.User

class LoginViewModel : ViewModel() {

    private val registerLiveData = MutableLiveData<User>()

    val userRegisterLiveData = Transformations.switchMap(registerLiveData) { user ->
        Repository.register(user)
    }

    fun register(user: User) {
        registerLiveData.value = user
    }


    private val loginLiveData = MutableLiveData<User>()

    val userLoginLiveData = Transformations.switchMap(loginLiveData) { user ->
        Repository.login(user)
    }

    fun login(user: User) {
        loginLiveData.value = user
    }

    fun saveUser(saveUser: SaveUser) = Repository.saveUser(saveUser)
}