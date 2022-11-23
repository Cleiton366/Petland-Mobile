package com.example.petland_mobile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.petland_mobile.databinding.ActivityPetInfoBinding
import com.example.petland_mobile.models.Pet
import com.example.petland_mobile.models.User
import com.example.petland_mobile.models.PetInfo
import com.example.petland_mobile.models.petList
import com.facebook.drawee.view.SimpleDraweeView

class PetInfo : AppCompatActivity() {

    private lateinit var  binding: ActivityPetInfoBinding
    private lateinit var user : User
    private lateinit var pet : Pet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //getting user info
        val petInfo = intent.extras?.get("petInfo") as? PetInfo
        petInfo?.user ?.let {
            user = petInfo.user!!
        }
        petInfo?.pet ?.let {
            pet = petInfo.pet!!
        }

        binding = ActivityPetInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //loadUserImg(user.avatarurl)

        val pet  = intent.extras?.get("pet")

        updateUI(petInfo as PetInfo)

        //open user profile
            val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
            imageView.setOnClickListener {
                val intent = Intent(this,  ProfileActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }

    }

    private fun updateUI(petInfo: PetInfo) {
        val petPhoto = findViewById<SimpleDraweeView>(R.id.pet_photo)
        val petName = findViewById<TextView>(R.id.pet_name)
        val petAge = findViewById<TextView>(R.id.pet_age)
        val petCity = findViewById<TextView>(R.id.pet_city)
        val medicalCond = findViewById<TextView>(R.id.pet_medical_condition)
        val profilePhoto = findViewById<SimpleDraweeView>(R.id.profile_image)
        val profile_image_donation = findViewById<SimpleDraweeView>(R.id.profile_image_donation)
        val username1 = findViewById<TextView>(R.id.username1)

        petName.setText("Name: " + petInfo.pet?.petname)
        petPhoto.setImageURI( petInfo.pet?.petphoto)
        petAge.setText("Age: " + petInfo.pet?.age)
        petCity.setText("City: " + petInfo.pet?.city)
        medicalCond.setText("Medical Condition: " + petInfo.pet?.medicalcondition)
        profilePhoto.setImageURI(petInfo.user?.avatarurl)
        profile_image_donation.setImageURI(petInfo.pet?.donatorInfo?.avatarurl)

        username1.setText(petInfo.pet?.donatorInfo?.username)


    }

    private fun petFromPos(petPos: Int): Pet? {

        for( pets in petList ){
            if(pets.petid == petPos.toString()){
                return pets
            }
        }
        return null
    }

    private fun loadUserImg(url : String) {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(Uri.parse(url))
    }

}