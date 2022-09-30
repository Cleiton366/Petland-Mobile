package com.example.petland_mobile

import com.example.petland_mobile.models.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeActivity : AppCompatActivity() {
    private lateinit var user : User
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        //getting user info
        val newUser = intent.extras?.get("user") as? User
        newUser ?.let {
            user = newUser
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}