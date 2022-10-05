package com.example.petland_mobile

import android.content.res.Resources
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.petland_mobile.models.Cities
import com.example.petland_mobile.models.States
import com.example.petland_mobile.models.User
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.runBlocking

class AdoptionForm : AppCompatActivity() {
    private lateinit var user : User
    var cities = arrayListOf<Cities>()
    private var states: MutableList<States> = ArrayList()

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
        var imageView = findViewById<SimpleDraweeView>(R.id.pet_photo_preview)
        imageView.isVisible = false

        //setting pet_medical_condition spinner values
        val spinnerMedicalCondition: Spinner = findViewById(R.id.spinner_pet_medical_condition)
        spinnerMedicalCondition.adapter = ArrayAdapter<String>(this, R.layout.spinner_item, medicalOptions)

        //setting pet_age spinner values
        val spinnerAge: Spinner = findViewById(R.id.spinner_pet_age)
        spinnerAge.adapter = ArrayAdapter<String>(this, R.layout.spinner_item, ageOptions)

        //loading user profile picture
        imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(Uri.parse(user.avatarurl))

        //loading states and cities
        fetchStates()
        val spinnerStates : Spinner = findViewById(R.id.spinner_State)
        val statesNameArr = states.map {it.name}
        spinnerStates.adapter = ArrayAdapter<String>(this, R.layout.spinner_item, statesNameArr)

        spinnerStates.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedState = states[position].iso2
                fetchCities(selectedState)

                val spinnerCities : Spinner = findViewById(R.id.spinner_city)
                spinnerCities.adapter = ArrayAdapter<String>(this@AdoptionForm, R.layout.spinner_item, cities.map {it.name})
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun fetchStates() {
        runBlocking {
            val url = "https://api.countrystatecity.in/v1/countries/BR/states"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val res: HttpResponse = client.get(url) {
                headers {
                    append("X-CSCAPI-KEY", BuildConfig.Country_State_City_API)
                }
            }
            states = res.body()
        }
    }

    private fun fetchCities(state : String) {
        runBlocking {
            val url = "https://api.countrystatecity.in/v1/countries/BR/states/$state/cities"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val res: HttpResponse = client.get(url) {
                headers {
                    append("X-CSCAPI-KEY", BuildConfig.Country_State_City_API)
                }
            }
            cities = res.body()
        }
    }

}