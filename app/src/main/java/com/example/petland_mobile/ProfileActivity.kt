package com.example.petland_mobile

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.petland_mobile.models.User
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

class ProfileActivity : AppCompatActivity() {
    private lateinit var user : User
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        //getting user info
        val newUser = intent.extras?.get("user") as? User
        newUser ?.let {
            user = newUser
        }
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_profile)
        loadUserInfo()
    }

    private fun loadUserInfo() {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(user.avatarurl)

        val profileImageView = findViewById<SimpleDraweeView>(R.id.profile_image_full)
        profileImageView.setImageURI(user.avatarurl)

        val userName = findViewById<TextView>(R.id.username)
        userName.text = user.username
    }
}