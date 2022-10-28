package com.example.petland_mobile

import android.content.Intent
import android.net.Uri
import com.example.petland_mobile.models.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

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
        Fresco.initialize(this)
        setContentView(R.layout.activity_home)
        loadUserImg(user.avatarurl)

        //submit adoption form card event handler
        val submitAdoptionCard : CardView = findViewById(R.id.submitPetCard)
        submitAdoptionCard.setOnClickListener {
            val intent = Intent(this,  AdoptionForm::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
        
        //open user profile
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setOnClickListener {
            val intent = Intent(this,  ProfileActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }

    private fun loadUserImg(url : String) {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(url)
    }
}