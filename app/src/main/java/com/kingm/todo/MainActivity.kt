package com.kingm.todo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.kingm.todo.network.Api
import com.kingm.todo.user.UserInfoActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val openUserInfoActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.e("result", result.toString())
        //var task = result.data?.getSerializableExtra("task") as Task?
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        val userInfoTextView = findViewById<TextView>(R.id.userInfo)
        val avatarImageView = findViewById<ImageView>(R.id.profile_photo)
        super.onResume()
        /*avatarImageView.load("https://goo.gl/gEgYUd") {
            transformations(
                //CircleCropTransformation()
                RoundedCornersTransformation(8F)
            )
            build()
        }*/
        lifecycleScope.launch {
            val userInfo = Api.userWebService.getInfo().body()!!
            Log.e("userInfo", userInfo.firstName + " " +  userInfo.lastName)
            userInfoTextView.text = "${userInfo.firstName} ${userInfo.lastName}"
            val avatarImageView = findViewById<ImageView>(R.id.profile_photo)
            avatarImageView.load(userInfo.avatar) {
                error(R.drawable.ic_launcher_background) // affiche une image par défaut en cas d'erreur:
            }
        }

        avatarImageView.setOnClickListener{
            val intent = Intent(this, UserInfoActivity::class.java)
            openUserInfoActivity.launch(intent)
        }
    }
}