package com.example.petland_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}