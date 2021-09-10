package com.android.flowerart.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.flowerart.FlowerApplication
import com.android.flowerart.MainActivity
import com.android.flowerart.R
import com.android.flowerart.logic.bean.SaveUser
import com.android.flowerart.logic.bean.User

class LoginActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.register).setOnClickListener {
            val nickName = findViewById<EditText>(R.id.account).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            viewModel.register(User(0, nickName, password))
        }

        findViewById<Button>(R.id.login).setOnClickListener {
            val account = findViewById<EditText>(R.id.account).text.toString().toInt()
            val password = findViewById<EditText>(R.id.password).text.toString()
            viewModel.login(User(account, null, password))
        }

        viewModel.userRegisterLiveData.observe(this, { result ->
            val user = result.getOrNull()
            if (user != null) {
                FlowerApplication.account = user.account
                val saveUser = SaveUser(user.account, user.nickName!!, user.password)
                viewModel.saveUser(saveUser)
                finish()
            } else {
                Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.userLoginLiveData.observe(this, { result ->
            val user = result.getOrNull()
            if (user != null) {
                FlowerApplication.account = user.account
                val saveUser = SaveUser(user.account, user.nickName!!, user.password)
                viewModel.saveUser(saveUser)
                finish()
            } else {
                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }
}