package com.example.petland_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.adapters.CardAdapter
import com.example.petland_mobile.databinding.ActivityProfileBinding
import com.example.petland_mobile.models.Pet
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

class ProfileActivity : AppCompatActivity() {
    private lateinit var user : User
    private lateinit var petsListDonated : MutableList<Pet>
    private lateinit var petsListAdopted : MutableList<Pet>

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        //getting user info
        val newUser = intent.extras?.get("user") as? User
        newUser ?.let {
            user = newUser
        }
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_profile)

        loadUserInfo()
        getPetsList()

        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view_donated_list)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val adapter = CardAdapter(petsListDonated)
        recyclerview.adapter = adapter
    }

    private fun loadUserInfo() {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(user.avatarurl)

        val profileImageView = findViewById<SimpleDraweeView>(R.id.profile_image_full)
        profileImageView.setImageURI(user.avatarurl)

        val userName = findViewById<TextView>(R.id.username)
        userName.text = user.username
    }

    private fun getPetsList () {
        var error = false
        runBlocking {
            var url = getString(R.string.server) + "/donatedPets"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val res: HttpResponse = client.get(url) {
                headers {
                    append("userid", user.id)
                }
            }
             if(res.status.value == 200) {
                petsListDonated = res.body()
                client.close()
            } else {
                error = true
                client.close()
            }
        }
        if(error) {
            val intent = Intent(this,  HomeActivity::class.java)
            intent.putExtra("user", user)
            Toast.makeText(this, "Error while fetching pets list", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}