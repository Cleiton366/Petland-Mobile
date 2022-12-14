package com.example.petland_mobile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.petland_mobile.databinding.ActivityPetInfoBinding
import com.example.petland_mobile.models.*
import com.example.petland_mobile.models.PetInfo
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.runBlocking

class PetInfo : AppCompatActivity() {

    private lateinit var user : User
    private lateinit var pet : Pet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_pet_info)

        //getting user info
        val petInfo = intent.extras?.get("petInfo") as? PetInfo
        petInfo?.user ?.let {
            user = petInfo.user!!
        }
        petInfo?.pet ?.let {
            pet = petInfo.pet!!
        }

        //loadUserImg(user.avatarurl)

        //val pet  = intent.extras?.get("pet")

        updateUI(petInfo as PetInfo)

        //open user profile
            val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
            imageView.setOnClickListener {
                val intent = Intent(this,  ProfileActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }

        //val requestButton : Button = findViewById(R.id.buttonRequest)
       // requestButton.setOnClickListener {
          //  requestPet()
        //}

        val donatorUserProfile : LinearLayout = findViewById(R.id.donator_userprofile_container)
        donatorUserProfile.setOnClickListener {
            if(user.id != pet.donatorInfo!!.id) {
                val profileInfo = ProfileInfo(user, pet.donatorInfo!!)
                val intent = Intent(this,  ProfileActivity::class.java)
                intent.putExtra("profileInfo", profileInfo)
                startActivity(intent)
            } else {
                val profileInfo = ProfileInfo(user)
                val intent = Intent(this,  ProfileActivity::class.java)
                intent.putExtra("profileInfo", profileInfo)
                startActivity(intent)
            }
        }

        val mapBtn : Button = findViewById(R.id.viewLocationBtn)
        mapBtn.setOnClickListener {
            val petAddress = PetAddress(pet.city, pet.sstate, pet.address)
            val intent = Intent(this,  MapsActivity::class.java)
            intent.putExtra("user", user)
            intent.putExtra("address", petAddress.toString())
            startActivity(intent)
        }
    }

    private fun requestPet(){
        var error = false
        runBlocking {
            var url = getString(R.string.server) + "/donationrequest/new"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val res: HttpResponse = client.post(url){
                contentType(ContentType.Application.Json)
                //setBody()
            }
            if(res.status.value == 200) {
                println("success")
            }else {
                error = true
                client.close()
            }
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