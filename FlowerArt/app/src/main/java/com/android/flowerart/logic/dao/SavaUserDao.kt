package com.android.flowerart.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.android.flowerart.FlowerApplication
import com.android.flowerart.logic.bean.SaveUser
import com.google.gson.Gson

object SaveUserDao {

    fun saveUser(saveUser: SaveUser) {
        sharedPreferences().edit {
            putString("saveUser", Gson().toJson(saveUser))
        }
    }

    fun getSaveUser(): SaveUser {
        val saveUserJson = sharedPreferences().getString("saveUser", "")
        return Gson().fromJson(saveUserJson, SaveUser::class.java)
    }

    fun isSaveUser() = sharedPreferences().contains("saveUser")

    fun deleteSaveUser() {
        sharedPreferences().edit {
            clear()
        }
    }

    private fun sharedPreferences() =
        FlowerApplication.context.getSharedPreferences("FlowerArt_User", Context.MODE_PRIVATE)
}