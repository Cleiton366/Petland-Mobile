package com.example.petland_mobile.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.Interface.AcceptDonationClickListener
import com.example.petland_mobile.Interface.RejectDonationClickListener
import com.example.petland_mobile.databinding.CardRequestCellBinding
import com.example.petland_mobile.models.DonationRequestInfo

class DonationRequestViewHolder(private val cardRequestCellBinding: CardRequestCellBinding,
                                private var acceptClickListener : AcceptDonationClickListener,
                                private var rejectClickListener : RejectDonationClickListener)
    : RecyclerView.ViewHolder(cardRequestCellBinding.root) {

    fun bindUser(donationRequestInfo: DonationRequestInfo, position : Int) {
        cardRequestCellBinding.requestListTittle.text = "${donationRequestInfo.User.username} wants to adopt " +
                "${donationRequestInfo.Pet.petname}"
        cardRequestCellBinding.requestListUserPhoto.setImageURI(donationRequestInfo.User.avatarurl)

        cardRequestCellBinding.acceptBtn.setOnClickListener{
            acceptClickListener.onClickAccept(donationRequestInfo, position)
        }

        cardRequestCellBinding.rejectBtn.setOnClickListener {
            rejectClickListener.onClickReject(donationRequestInfo, position)
        }
    }
}