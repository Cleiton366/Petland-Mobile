package com.example.petland_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.petland_mobile.models.*
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopt_research)

        fetchPetList ()
        allPetsListView ()
    }
}

    private fun fetchPetList () {
        runBlocking {
            var url = "http://192.168.0.6:4000/pet/"
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

private fun allPetsListView () {
    val listView : ListView = findViewById(R.id.allPetsListView)
    val pets = petList.map { it. }
    val arrayAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, pets)
    listView.adapter = arrayAdapter
}