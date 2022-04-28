package com.kingm.todo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.kingm.todo.network.Api
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        val userInfoTextView = findViewById<TextView>(R.id.userInfo)
        super.onResume()
        lifecycleScope.launch {
            val userInfo = Api.userWebService.getInfo().body()!!
            Log.e("userInfo", userInfo.firstName + " " +  userInfo.lastName)
            userInfoTextView.setText("${userInfo.firstName} ${userInfo.lastName}")
        }

    }
}