package com.example.petland_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.petland_mobile.databinding.ActivityPetInfoBinding
import com.example.petland_mobile.models.PET_EXTRA
import com.example.petland_mobile.models.Pet
import com.example.petland_mobile.models.User
import com.example.petland_mobile.models.petList
import com.facebook.drawee.view.SimpleDraweeView

class PetInfo : AppCompatActivity() {

    private lateinit var  binding: ActivityPetInfoBinding
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //getting user info
        val newUser = intent.extras?.get("user") as? User
        newUser ?.let {
            user = newUser
        }

       // loadUserImg(user.avatarurl)

        binding = ActivityPetInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pet  = intent.extras?.get("pet")
        val test = 1
        /*
        val petPos = intent.getIntExtra(PET_EXTRA, -1)
        val pets = petFromPos(petPos)
        if(pets != null){
            binding.petPhoto.setImageURI(pets.petphoto)
            binding.petName.text = pets.petname
            binding.petAge.text = pets.age
            binding.petCity.text = pets.city
            binding.petMedicalCondition.text = pets.medicalcondition
        }*/
        updateUI(pet as Pet)

        //open user profile
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setOnClickListener {
            val intent = Intent(this,  ProfileActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

    }

    private fun updateUI(pet : Pet) {
        val petPhoto = findViewById<SimpleDraweeView>(R.id.pet_photo)
        val petName = findViewById<TextView>(R.id.pet_name)
        val petAge = findViewById<TextView>(R.id.pet_age)
        val petCity = findViewById<TextView>(R.id.pet_city)
        val medicalCond = findViewById<TextView>(R.id.pet_medical_condition)

        petName.setText(pet.petname)
        petPhoto.setImageURI(pet.petphoto)
        petAge.setText(pet.age)
        petCity.setText(pet.city)
        medicalCond.setText(pet.medicalcondition)
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
        imageView.setImageURI(url)
    }

}