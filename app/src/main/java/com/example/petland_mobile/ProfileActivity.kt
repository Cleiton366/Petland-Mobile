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
import com.example.petland_mobile.adapters.PetCardAdapter
import com.example.petland_mobile.models.Pet
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
import kotlinx.coroutines.runBlocking

class ProfileActivity : AppCompatActivity() {
    private lateinit var user : User
    private lateinit var loggedUser: User
    private lateinit var petsListDonated : MutableList<Pet>
    private lateinit var petsListAdopted : MutableList<Pet>
    var isVisitingOtherProfile : Boolean = false
    private lateinit var userFriendlist: UserFriendlist

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        //getting user info
        val profileInfo = intent.extras?.get("profileInfo") as? ProfileInfo

        profileInfo?.user ?.let {
            user = profileInfo.user!!
        }
        profileInfo?.loggedUser ?.let {
            loggedUser = profileInfo.loggedUser
        }
        profileInfo?.isVisitingOtherProfile ?.let {
            isVisitingOtherProfile = profileInfo.isVisitingOtherProfile
        }

        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_profile)

        loadUserInfo()
        getPetsList()
        fetchUserFriendlist()

        updateUI()
    }

    private fun updateUI () {
        if(petsListAdopted.size > 0 || petsListDonated.size > 0) {
            //removing no pets message
            val noPetsMessage = findViewById<LinearLayout>(R.id.no_pets_container)
            noPetsMessage.isInvisible = true
            val noPetsMessageParams : LinearLayout.LayoutParams = noPetsMessage.getLayoutParams() as LinearLayout.LayoutParams
            noPetsMessageParams .height = 0

            //populating recyclerview with arrays data
            var recyclerview = findViewById<RecyclerView>(R.id.recycler_view_donated_list)
            var adapter = PetCardAdapter(petsListDonated)

            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter

            recyclerview = findViewById<RecyclerView>(R.id.recycler_view_adopted_list)
            adapter = PetCardAdapter(petsListAdopted)

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

        val followBtn = findViewById<LinearLayout>(R.id.follow_btn)
        if(!isVisitingOtherProfile) {
            followBtn.isInvisible = true
        } else {
            val isFollowing = isFollowing()
            val isFollowingText : TextView = findViewById(R.id.is_following_text)

            if(isFollowing) {
                isFollowingText.text = getString(R.string.following_user)
            } else isFollowingText.text = getString(R.string.not_following_user)
        }

        val userFollowers : TextView = findViewById(R.id.user_followers)
        userFollowers.text = "Followers ${userFriendlist.followersQtd}"

        val userFollowing : TextView = findViewById(R.id.user_following)
        userFollowing.text = "Followers ${userFriendlist.followingQtd}"

        followBtn.setOnClickListener {
            var isFollowingText = findViewById<TextView?>(R.id.is_following_text)

            if(isFollowingText.text == getString(R.string.following_user)) {
                unfollowUser()
                isFollowingText.text = getString(R.string.not_following_user)
            } else {
                followUser()
                isFollowingText.text = getString(R.string.following_user)
            }
        }
    }

    private fun loadUserInfo() {

        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(loggedUser.avatarurl)

        if (isVisitingOtherProfile) {
            val profileImageView = findViewById<SimpleDraweeView>(R.id.profile_image_full)
            profileImageView.setImageURI(user.avatarurl)

            val userName = findViewById<TextView>(R.id.username)
            userName.text = user.username
        } else {
            val profileImageView = findViewById<SimpleDraweeView>(R.id.profile_image_full)
            profileImageView.setImageURI(loggedUser.avatarurl)

            val userName = findViewById<TextView>(R.id.username)
            userName.text = loggedUser.username
        }

    }

    private fun getPetsList () {
        var error: Boolean = fetchDonatedList()

        if(error) {
            val intent = Intent(this,  HomeActivity::class.java)
            intent.putExtra("user", user)
            Toast.makeText(this, "Error while fetching pets list", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        error = fetchAdoptedList()

        if(error) {
            val intent = Intent(this,  HomeActivity::class.java)
            intent.putExtra("user", user)
            Toast.makeText(this, "Error while fetching pets list", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

    }

    private fun fetchDonatedList() : Boolean{

        var userId : String

        if (isVisitingOtherProfile) {
            userId = user.id
        } else userId = loggedUser.id

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
                    append("userid", userId)
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

        var userId : String

        if (isVisitingOtherProfile) {
            userId = user.id
        } else userId = loggedUser.id

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
                    append("userid", userId)
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

    private fun fetchUserFriendlist () {

        var userId : String
        if (isVisitingOtherProfile) {
            userId = user.id
        } else userId = loggedUser.id

        runBlocking {
            val url = getString(R.string.server) + "/social/user-social-info"
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
                userFriendlist = res.body()
                client.close()
            } else {
                client.close()
            }
        }
    }

    private fun followUser() {
        runBlocking {
            val social =
                Social(loggedUser.id, loggedUser.username, loggedUser.avatarurl,
                        user.id, user.username, user.avatarurl)
            val url = getString(R.string.server) + "/social/update-social-info"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            //fetching donated pets
            val res: HttpResponse = client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(social)
            }
            if(res.status.value == 200) {
                userFriendlist = res.body()
                client.close()
            } else {
                client.close()
            }
        }
    }

    private fun unfollowUser() {
        runBlocking {
            val url = getString(R.string.server) + "/social/delete-social-info"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            //fetching donated pets
            val res: HttpResponse = client.delete(url) {
                headers {
                    append("userid", loggedUser.id)
                }
            }
            if(res.status.value == 200) {
                userFriendlist = res.body()
                client.close()
            } else {
                client.close()
            }
        }
    }

    private fun isFollowing() : Boolean {
        var isFollowing = false
        val userId = user.id

        for (user in userFriendlist.following) {
            if(user.id == userId) isFollowing = true
        }

        return isFollowing
    }
}