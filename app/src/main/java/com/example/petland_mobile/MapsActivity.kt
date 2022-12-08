package com.example.petland_mobile

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petland_mobile.BuildConfig.GOOGLE_MAPS_API_KEY

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.petland_mobile.databinding.ActivityMapsBinding
import com.example.petland_mobile.models.ProfileInfo
import com.example.petland_mobile.models.User
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.util.*
import io.ktor.util.Identity.encode
import kotlinx.coroutines.runBlocking

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var loggedUser : User
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var address : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val newLoggedUser = intent.extras?.get("user") as? User
        newLoggedUser ?.let {
            loggedUser = newLoggedUser
        }

        val newAddress = intent.extras?.get("address") as? String
        newAddress ?.let {
            address = newAddress
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //open user profile
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setOnClickListener {
            val profileInfo = ProfileInfo(loggedUser)
            val intent = Intent(this,  ProfileActivity::class.java)
            intent.putExtra("profileInfo", profileInfo)
            startActivity(intent)
        }

        loadUserImg(loggedUser.avatarurl)
    }
    private fun loadUserImg(url : String) {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(url)
    }

    private fun getLatLng(address : String): LatLng? {
        val geocoder = Geocoder(this)
        val address : MutableList<Address>? = geocoder.getFromLocationName(address, 1)
        return address?.get(0)?.let { LatLng(it.latitude, address?.get(0).longitude) }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val location = getLatLng(address)
        mMap.setMinZoomPreference(15.0f)
        mMap.addMarker(MarkerOptions().position(location!!).title("Pet is Here!!"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location!!))
    }
}