package com.example.petland_mobile.models

import java.io.Serializable

class DonationRequest : Serializable {
    lateinit var donationrequestid : String
    lateinit var donatorid : String
    lateinit var interresteddoneeid : String
    lateinit var petid : String
    var isadopted = false
}