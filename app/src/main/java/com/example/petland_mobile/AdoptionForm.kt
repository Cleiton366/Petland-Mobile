package com.example.petland_mobile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.petland_mobile.models.*
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
import io.ktor.http.cio.*
import io.ktor.serialization.gson.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.File

class AdoptionForm : AppCompatActivity() {

    private lateinit var user : User
    var cities = arrayListOf<Cities>()
    private var states: MutableList<States> = ArrayList()
    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var petType : String

    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_adoption_form)
        updateUI()
    }

    private fun updateUI() {

        //getting user info
        val newUser = intent.extras?.get("user") as? User
        newUser ?.let {
            user = newUser
        }

        val res: Resources = resources
        val ageOptions = res.getStringArray(R.array.pet_age_options)
        val medicalOptions = res.getStringArray(R.array.pet_medical_condition_options)

        //hiding pet image_preview
        var imageView = findViewById<SimpleDraweeView>(R.id.pet_photo_preview)
        imageView.isVisible = false

        //setting pet_medical_condition spinner values
        val spinnerMedicalCondition: Spinner = findViewById(R.id.spinner_pet_medical_condition)
        spinnerMedicalCondition.adapter = ArrayAdapter(this, R.layout.spinner_item, medicalOptions)

        //setting pet_age spinner values
        val spinnerAge: Spinner = findViewById(R.id.spinner_pet_age)
        spinnerAge.adapter = ArrayAdapter(this, R.layout.spinner_item, ageOptions)

        //loading user profile picture
        imageView = findViewById(R.id.profile_image)
        imageView.setImageURI(Uri.parse(user.avatarurl))

        //loading states and cities
        fetchStates()
        val spinnerStates : Spinner = findViewById(R.id.spinner_State)
        val statesNameArr = states.map {it.name}
        spinnerStates.adapter = ArrayAdapter(this, R.layout.spinner_item, statesNameArr)

        spinnerStates.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedState = states[position].iso2
                fetchCities(selectedState)

                val spinnerCities : Spinner = findViewById(R.id.spinner_city)
                spinnerCities.adapter = ArrayAdapter(this@AdoptionForm, R.layout.spinner_item, cities.map {it.name})
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        //getting ratio values
        val estimateRatio = findViewById<RadioGroup>(R.id.pet_type)
        estimateRatio.setOnCheckedChangeListener{ _, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                petType = radio.text.toString()
            }


        //return to home activity
        val cancelBtn : Button = findViewById(R.id.cancel_form_btn)
        cancelBtn.setOnClickListener {
            val intent = Intent(this,  HomeActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        //submit form
        val submitBtn : Button = findViewById(R.id.submit_form_btn)
        submitBtn.setOnClickListener {
            submitForm()
        }

        //load pet photo
        val submitPetPhoto : LinearLayout = findViewById(R.id.pet_photo_container)
        submitPetPhoto.setOnClickListener {
            checkStoragePermission()
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            val imageView = findViewById<SimpleDraweeView>(R.id.pet_photo_preview)
            imageUri = data?.data
            imageView.setImageURI(imageUri.toString())
            imageView.isVisible = true
        }
    }

    private fun checkStoragePermission() {
        val permission =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private fun submitForm() {
        val petName = findViewById<EditText>(R.id.pet_name).text.toString()
        val petState = findViewById<Spinner>(R.id.spinner_State).selectedItem.toString()
        val petCity = findViewById<Spinner>(R.id.spinner_city).selectedItem.toString()
        val petMedicalCondition = findViewById<Spinner>(R.id.spinner_pet_medical_condition).selectedItem.toString()
        val petAge : Int = findViewById<Spinner>(R.id.spinner_pet_age).selectedItem.toString().toInt()
        val petType = petType
        var success = false

        val petAddress = PetAddress(petCity, petState)
        val pet = Pet (user.id, petName, petAge, petMedicalCondition, petType, petAddress)

        val file = File(getRealPathFromURI(this,imageUri!!));

        runBlocking {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val res: HttpResponse = client.submitFormWithBinaryData(
                url = getString(R.string.server) + "/pet/new",
                formData = formData {
                    append("pet", Gson().toJson(pet))
                    append("image", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=\"image.png\"")
                    })
                }
            )
            if(res.status.value == 200) {
                success = true
                client.close()
            } else client.close()
        }

        //TODO remove this after testing
        //TODO redirect user to pet profile when the page its done
        if(success) {
            Toast.makeText(this, "Pet posted for donation", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(this, "Error while trying to add Pet for donation", Toast.LENGTH_SHORT).show()
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            cursor!!.getString(column_index)
        } finally {
            cursor?.close()
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