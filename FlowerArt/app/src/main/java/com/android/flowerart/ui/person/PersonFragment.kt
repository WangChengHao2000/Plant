package com.android.flowerart.ui.person

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.flowerart.FlowerApplication
import com.android.flowerart.R
import com.android.flowerart.ui.login.LoginActivity

class PersonFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(PersonViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.findViewById<Button>(R.id.userLogin)?.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivityForResult(intent, 10)
        }

        if (FlowerApplication.account == 0) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivityForResult(intent, 10)
        }

        activity?.findViewById<Button>(R.id.logout)?.setOnClickListener {
            viewModel.deleteSaveUser()
            FlowerApplication.account = 0
            activity?.findViewById<Button>(R.id.userLogin)?.visibility = View.VISIBLE
            activity?.findViewById<LinearLayout>(R.id.userStatus)?.visibility = View.GONE
            activity?.findViewById<Button>(R.id.logout)?.visibility = View.GONE
        }

    }

    override fun onResume() {
        super.onResume()
        if (FlowerApplication.account != 0) {
            activity?.findViewById<Button>(R.id.userLogin)?.visibility = View.GONE
            activity?.findViewById<LinearLayout>(R.id.userStatus)?.visibility = View.VISIBLE
            activity?.findViewById<TextView>(R.id.userAccount)?.text =
                FlowerApplication.account.toString()
            activity?.findViewById<Button>(R.id.logout)?.visibility = View.VISIBLE
        } else {
            activity?.findViewById<Button>(R.id.userLogin)?.visibility = View.VISIBLE
            activity?.findViewById<LinearLayout>(R.id.userStatus)?.visibility = View.GONE
            activity?.findViewById<Button>(R.id.logout)?.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("user", "user")
        when (requestCode) {
            10 -> {

            }
        }
    }

}