package com.example.petland_mobile

import android.content.res.Resources
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isVisible
import com.example.petland_mobile.models.User
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

class AdoptionForm : AppCompatActivity() {
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
        setContentView(R.layout.activity_adoption_form)

        updateUI()
    }

    private fun updateUI() {

        val res: Resources = resources
        val ageOptions = res.getStringArray(R.array.pet_age_options)
        val medicalOptions = res.getStringArray(R.array.pet_medical_condition_options)

        //hiding pet image_preview
        val imageView = findViewById<SimpleDraweeView>(R.id.pet_photo_preview)
        imageView.isVisible = false

        //setting pet_medical_condition spinner values
        val spinnerMedicalCondition: Spinner = findViewById(R.id.spinner_pet_medical_condition)
        spinnerMedicalCondition.adapter = ArrayAdapter<String>(this, R.layout.spinner_item, medicalOptions)

        //setting pet_age spinner values
        val spinnerAge: Spinner = findViewById(R.id.spinner_pet_age)
        spinnerAge.adapter = ArrayAdapter<String>(this, R.layout.spinner_item, ageOptions)

        //loading user profile picture
        loadUserImg(user.avatarurl)
    }

    private fun loadUserImg(url : String) {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(Uri.parse(url))
    }
}