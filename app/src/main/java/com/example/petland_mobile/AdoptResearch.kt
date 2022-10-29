package com.example.petland_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import com.example.petland_mobile.models.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.adapters.AdapterCard
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.runBlocking
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*


class AdoptResearch : AppCompatActivity() {

    private lateinit var petList : MutableList<Pet>
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {

        //getting user info
        val newUser = intent.extras?.get("user") as? User
        newUser ?.let {
            user = newUser
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopt_research)

        loadUserImg(user.avatarurl)

        fetchPetList()
        allPetsListView()

    }

    private fun allPetsListView(){

        val recyclerView : RecyclerView = findViewById(R.id.adoptCatCard)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = AdapterCard(petList)
        recyclerView.adapter = adapter

    }

    private fun fetchPetList () {
        runBlocking {
            var url = getString(R.string.server) + "/pet"
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
