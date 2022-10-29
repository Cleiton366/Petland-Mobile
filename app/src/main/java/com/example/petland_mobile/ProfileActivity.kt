package com.example.petland_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.adapters.CardAdapter
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

        if(petsListAdopted.size > 0 || petsListDonated.size > 0) {
            //removing no pets message
            val noPetsMessage = findViewById<LinearLayout>(R.id.no_pets_container)
            noPetsMessage.isInvisible = true
            val noPetsMessageParams : LinearLayout.LayoutParams = noPetsMessage.getLayoutParams() as LinearLayout.LayoutParams
            noPetsMessageParams .height = 0

            //populating recyclerview with arrays data
            var recyclerview = findViewById<RecyclerView>(R.id.recycler_view_donated_list)
            var adapter = CardAdapter(petsListDonated)

            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter

            recyclerview = findViewById<RecyclerView>(R.id.recycler_view_adopted_list)
            adapter = CardAdapter(petsListAdopted)

            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter
        }
        if (petsListAdopted.size == 0) {
            val listContainer = findViewById<LinearLayout>(R.id.adopted_pets_container)
            listContainer.isVisible = false
        } else if (petsListDonated.size == 0) {
            val listContainer = findViewById<LinearLayout>(R.id.donated_pets_container)
            listContainer.isVisible = false
        }

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
        var error : Boolean = fetchAdoptedList()
        error = fetchDonatedList()
        if(error) {
            val intent = Intent(this,  HomeActivity::class.java)
            intent.putExtra("user", user)
            Toast.makeText(this, "Error while fetching pets list", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }

    private fun fetchDonatedList() : Boolean{
        var error = false
        runBlocking {
            var url = getString(R.string.server) + "/donatedPets"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            //fetching donated pets
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
        return error
    }

    private fun fetchAdoptedList() : Boolean {
        var error = false
        runBlocking {
            var url = getString(R.string.server) + "/userPets"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            //fetching donated pets
            val res: HttpResponse = client.get(url) {
                headers {
                    append("userid", user.id)
                }
            }
            if(res.status.value == 200) {
                petsListAdopted = res.body()
                client.close()
            } else {
                error = true
                client.close()
            }
        }
        return error
    }
}