package com.example.petland_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.Interface.AcceptDonationClickListener
import com.example.petland_mobile.Interface.RejectDonationClickListener
import com.example.petland_mobile.databinding.CardRequestCellBinding
import com.example.petland_mobile.holders.DonationRequestViewHolder
import com.example.petland_mobile.models.DonationRequestInfo
import com.example.petland_mobile.models.Social
import com.example.petland_mobile.models.User

class RequestCardAdapter (private val donationRequestsList : MutableList<DonationRequestInfo>,
                          private val acceptClickListener: AcceptDonationClickListener,
                          private val rejectClickListener : RejectDonationClickListener)
    : RecyclerView.Adapter<DonationRequestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationRequestViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardRequestCellBinding.inflate(from, parent, false)
        return DonationRequestViewHolder(binding, acceptClickListener, rejectClickListener)
    }

    override fun onBindViewHolder(holder: DonationRequestViewHolder, position: Int) {
        holder.bindUser(donationRequestsList[position], position)
    }

    override fun getItemCount(): Int = donationRequestsList.size
}