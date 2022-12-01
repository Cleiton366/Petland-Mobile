package com.example.petland_mobile.Interface

import com.example.petland_mobile.models.DonationRequestInfo

interface AcceptDonationClickListener {
    fun onClickAccept(donationRequestInfo: DonationRequestInfo, position : Int)
}