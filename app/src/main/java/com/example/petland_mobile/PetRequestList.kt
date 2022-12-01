package com.example.petland_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.Interface.AcceptDonationClickListener
import com.example.petland_mobile.Interface.RejectDonationClickListener
import com.example.petland_mobile.adapters.RequestCardAdapter
import com.example.petland_mobile.models.DonationRequestInfo
import com.example.petland_mobile.models.User
import com.facebook.drawee.view.SimpleDraweeView
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.runBlocking

class PetRequestList : AppCompatActivity(), AcceptDonationClickListener, RejectDonationClickListener {

    private lateinit var loggedUser : User
    private lateinit var donationRequestsList : MutableList<DonationRequestInfo>
    private lateinit var acceptClickListener: AcceptDonationClickListener
    private lateinit var rejectClickListener: RejectDonationClickListener

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()
        //getting user info
        val newUser = intent.extras?.get("user") as? User
        newUser ?.let {
            loggedUser = newUser
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_request_list)

        loadUserInfo()
        fetchDonationRequestList()

        //populating recyclerview with arrays data
        var recyclerview = findViewById<RecyclerView>(R.id.requestList_recyclerview)
        var adapter = RequestCardAdapter(donationRequestsList, acceptClickListener, rejectClickListener)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

    }

    private fun loadUserInfo() {
        val imageView = findViewById<SimpleDraweeView>(R.id.profile_image)
        imageView.setImageURI(loggedUser.avatarurl)
    }

    private fun fetchDonationRequestList () {
        runBlocking {
            val url = getString(R.string.server) + "/donationrequest/list"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            //fetching donated pets
            val res: HttpResponse = client.get(url) {
                headers {
                    append("userid", loggedUser.id)
                }
            }
            if(res.status.value == 200) {
                donationRequestsList = res.body()
                client.close()
            } else {
                client.close()
            }
        }
    }

    override fun onClickAccept(donationRequestInfo: DonationRequestInfo, position : Int) {
        runBlocking {
            val url = getString(R.string.server) + "/donationrequest/accept"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            //fetching donated pets
            val res: HttpResponse = client.post(url) {
                setBody(donationRequestInfo.DonationRequest)
            }
            if(res.status.value == 200) {
                donationRequestsList.removeAt(position)
                updateRecycleView()
                client.close()
                updateUI()
            } else {
                client.close()
            }
        }
    }

    override fun onClickReject(donationRequestInfo: DonationRequestInfo, position : Int) {
        runBlocking {
            val url = getString(R.string.server) + "/donationrequest/reject"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            //fetching donated pets
            val res: HttpResponse = client.delete(url) {
                headers {
                    append("donationRequestId", donationRequestInfo.DonationRequest.donationrequestid)
                }
            }
            if(res.status.value == 200) {
                donationRequestsList.removeAt(position)
                updateRecycleView()
                client.close()
            } else {
                client.close()
            }
        }
    }

    private fun updateRecycleView() {
        runOnUiThread {
            var recyclerview = findViewById<RecyclerView>(R.id.requestList_recyclerview)
            var adapter = RequestCardAdapter(donationRequestsList, acceptClickListener, rejectClickListener)

            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter
        }
    }

    private fun updateUI () {
        val intent = Intent(this, ChatList::class.java)
        intent.putExtra("user", loggedUser)
        startActivity(intent)
    }

}