package com.example.petland_mobile

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import com.example.petland_mobile.models.*
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import io.ktor.client.*
import io.ktor.client.call.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.runBlocking
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*


class AdoptResearch : AppCompatActivity() {
    private var petList : MutableList<Pet> = ArrayList()
    private lateinit var user : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_adopt_research)
        supportActionBar?.hide()

        //getting user info
        val newUser = intent.extras?.get("user") as? User
        newUser ?.let {
            user = newUser
        }

        Fresco.initialize(this)
        setContentView(R.layout.activity_adopt_research)
        loadUserImg(user.avatarurl)

        fetchPetList()
        allPetsListView()

    }

    private fun allPetsListView(){
        val listView : ListView = findViewById(R.id.allPetsListView)
        val pets = petList.map{it}
        val arrayAdapter : ArrayAdapter<Pet> = ArrayAdapter(this, R.layout.activity_adopt_research, pets)
        listView.adapter = arrayAdapter
    }

    private fun fetchPetList () {
        runBlocking {
            var url = getString(R.string.server)
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val res: HttpResponse = client.get(url)
            if(res.status.value == 200) {
                petList = res.body()
            }
            client.close()
        }
    }

    private fun loadUserImg(url : String) {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(Uri.parse(url))
    }
}
