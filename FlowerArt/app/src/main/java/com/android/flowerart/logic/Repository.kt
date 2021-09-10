package com.android.flowerart.logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.android.flowerart.logic.bean.Flower
import com.android.flowerart.logic.bean.Record
import com.android.flowerart.logic.bean.SaveUser
import com.android.flowerart.logic.bean.User
import com.android.flowerart.logic.dao.SaveUserDao
import com.android.flowerart.logic.network.FlowerNetwork
import com.android.flowerart.utils.PicUtil
import com.android.flowerart.utils.RecognizeUtil
import kotlinx.coroutines.Dispatchers
import java.io.File
import kotlin.coroutines.CoroutineContext

object Repository {

    fun register(user: User) = fire(Dispatchers.IO) {
        val res = FlowerNetwork.register(user)
        if (res.account != 0) {
            Result.success(res)
        } else {
            Result.failure(RuntimeException("failure"))
        }
    }

    fun login(user: User) = fire(Dispatchers.IO) {
        val res = FlowerNetwork.login(user)
        if (res.nickName != null) {
            Result.success(res)
        } else {
            Result.failure(RuntimeException("failure"))
        }
    }

    fun insertRecord(record: Record) = fire(Dispatchers.IO) {
        val res = FlowerNetwork.insertRecord(record)
        if (res.recordID != 0) {
            Result.success(res)
        } else {
            Result.failure(RuntimeException("failure"))
        }
    }

    fun selectRecord(account: Int) = fire(Dispatchers.IO) {
        val res = FlowerNetwork.selectRecord(account)
        if (res != null) {
            Result.success(res)
        } else {
            Result.failure(RuntimeException("failure"))
        }
    }

    fun selectAllRecord() = fire(Dispatchers.IO) {
        val res = FlowerNetwork.selectAllRecord()
        if (res != null) {
            Result.success(res)
        } else {
            Result.failure(RuntimeException("failure"))
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun recognize(filePath: String): LiveData<List<Flower>> {
        val results = RecognizeUtil.recognize(filePath)
        val liveData = MutableLiveData<List<Flower>>()
        if (results.result != null)
            liveData.value = results.result
        else
            liveData.value = null
        return liveData
    }

    fun uploadPic(file: File): String {
        return PicUtil.uploadPic(file)
    }

    fun saveUser(saveUser: SaveUser) = SaveUserDao.saveUser(saveUser)
    fun getSaveUser() = SaveUserDao.getSaveUser()
    fun isSaveUser() = SaveUserDao.isSaveUser()
    fun deleteSaveUser() = SaveUserDao.deleteSaveUser()

}