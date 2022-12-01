package com.example.petland_mobile.Interface

import com.example.petland_mobile.models.DonationRequestInfo

interface RejectDonationClickListener {
    fun onClickReject(donationRequestInfo: DonationRequestInfo, position : Int)
}