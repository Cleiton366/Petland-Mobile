package com.example.petland_mobile

import android.content.Intent
import com.example.petland_mobile.models.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.petland_mobile.models.ProfileInfo
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

class HomeActivity : AppCompatActivity() {
    private lateinit var loggedUser : User
    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()

        //getting user info
        val newLoggedUser = intent.extras?.get("user") as? User
        newLoggedUser ?.let {
            loggedUser = newLoggedUser
        }

        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_home)
        loadUserImg(loggedUser.avatarurl)

        //adopt cat
        val adoptResearchCat : CardView = findViewById(R.id.adoptCatCard)
        adoptResearchCat.setOnClickListener { val intent = Intent (this, AdoptResearch::class.java)
            intent.putExtra("user", loggedUser)
            startActivity(intent) }

        //adopt Dog
        val adoptResearchDog : CardView = findViewById(R.id.adoptDogCard)
        adoptResearchDog.setOnClickListener { val intent = Intent (this, DogResearch::class.java)
        intent.putExtra("user", loggedUser)
            startActivity(intent)
        }

        //submit adoption form card event handler
        val submitAdoptionCard : CardView = findViewById(R.id.submitPetCard)
        submitAdoptionCard.setOnClickListener {
            val intent = Intent(this,  AdoptionForm::class.java)
            intent.putExtra("user", loggedUser)
            startActivity(intent)
        }
        
        //open user profile
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setOnClickListener {
            val profileInfo = ProfileInfo(loggedUser)
            val intent = Intent(this,  ProfileActivity::class.java)
            intent.putExtra("profileInfo", profileInfo)
            startActivity(intent)
        }
        //Open request list
        val requestListCard : CardView = findViewById(R.id.request_Card)
        requestListCard.setOnClickListener {
            val intent = Intent(this, PetRequestList::class.java)
            intent.putExtra("user", loggedUser)
            startActivity(intent)
        }

    }

    private fun loadUserImg(url : String) {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(url)
    }
}