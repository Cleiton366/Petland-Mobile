package com.example.petland_mobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.Interface.PetClickListener
import com.example.petland_mobile.adapters.AdapterCard
import com.example.petland_mobile.models.*
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


class AdoptResearch : AppCompatActivity(), PetClickListener {

    private lateinit var petList : MutableList<Pet>
    private lateinit var user : User
    private lateinit var petInfo : com.example.petland_mobile.models.PetInfo

    override fun onCreate(savedInstanceState: Bundle?) {

        //getting user info
        val newUser = intent.extras?.get("user") as? User
        newUser ?.let {
            user = newUser
        }

        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_adopt_research)

        loadUserImg(user.avatarurl)

        val userName = intent.extras?.get("user") as? com.example.petland_mobile.models.PetInfo
        userName ?.let {
            petInfo = userName
        }

        fetchPetList()
        allPetsListView()

        /*//open user profile
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setOnClickListener {
            val intent = Intent(this,  ProfileActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }*/
    }

    override fun onClick(pet: Pet) {
        val petInfo = PetInfo(pet, user)
        val intent = Intent(applicationContext, PetInfo::class.java)
        intent.putExtra("petInfo", petInfo)
        startActivity(intent)
    }

    private fun allPetsListView(){

        val recyclerView : RecyclerView = findViewById(R.id.recycler_view_cat_Card)
        val context = this
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterCard(petList, context)
        }
    }

    private fun fetchPetList () : Boolean{
        var error = false
        runBlocking {
            var url = getString(R.string.server) + "/pet/Cat/all"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val res: HttpResponse = client.get(url)
            if(res.status.value == 200) {
                petList = res.body()
                client.close()
            }else {
                error = true
                client.close()
            }
        }
        return error
    }

    private fun loadUserImg(url : String) {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(Uri.parse(url))
    }

}
