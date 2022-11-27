package com.example.petland_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petland_mobile.Interface.UserClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.adapters.AdapterCard
import com.example.petland_mobile.adapters.UserCardAdapter
import com.example.petland_mobile.models.ProfileInfo
import com.example.petland_mobile.models.User
import com.example.petland_mobile.models.UserFriendlist
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

class FriendList : AppCompatActivity(), UserClickListener {
    private lateinit var loggedUser : User
    private lateinit var userFriendlist: UserFriendlist
    private lateinit var viewType : String

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()

        //getting user info
        val newUser = intent.extras?.get("user") as? User
        newUser ?.let {
            loggedUser = newUser
        }
        //get friendlist variables
        val newFriendlist = intent.extras?.get("friendlist") as? UserFriendlist
        newFriendlist ?.let {
            userFriendlist = newFriendlist
        }

        val newViewType = intent.extras?.get("viewType") as? String
        newViewType ?.let {
            viewType = newViewType
        }

        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_friend_list)

        loadUserInfo()

        //populating recyclerview with arrays data
        if(viewType == "followers") {
            var recyclerview = findViewById<RecyclerView>(R.id.friendlist_recyclerview)
            var adapter = UserCardAdapter(userFriendlist.followers, viewType, this)
            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter
        } else {
            var recyclerview = findViewById<RecyclerView>(R.id.friendlist_recyclerview)
            var adapter = UserCardAdapter(userFriendlist.following, viewType, this)
            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter
        }
    }

    override fun onClick(userId : String) {
        if(userId == loggedUser.id) {
            val profileInfo = ProfileInfo(loggedUser)
            val intent = Intent(this,  ProfileActivity::class.java)
            intent.putExtra("profileInfo", profileInfo)
            startActivity(intent)
        } else {
            val user : User = getUserInfo(userId)
            val profileInfo = ProfileInfo(loggedUser, user)
            val intent = Intent(this,  ProfileActivity::class.java)
            intent.putExtra("profileInfo", profileInfo)
            startActivity(intent)
        }
    }

    private fun loadUserInfo() {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(loggedUser.avatarurl)
    }

    private fun getUserInfo(userId : String): User {
        lateinit var user : User
        runBlocking {
            var url = getString(R.string.server) + "/get-user"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            //fetching donated pets
            val res: HttpResponse = client.get(url) {
                headers {
                    append("userid", userId)
                }
            }
            if(res.status.value == 200) {
                user = res.body()
                client.close()
            } else {
                client.close()
            }
        }
        return user
    }

}