package com.example.petland_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.Interface.ChatClickListener
import com.example.petland_mobile.databinding.CardRequestCellBinding
import com.example.petland_mobile.holders.RequestMenViewHolder
import com.example.petland_mobile.models.User

class RequestCardAdapter (private val requestlist : List<User>, private val clickListener: ChatClickListener)
    : RecyclerView.Adapter<RequestMenViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestMenViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardRequestCellBinding.inflate(from, parent, false)
        return RequestMenViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RequestMenViewHolder, position: Int) {
        holder.bindUser(requestlist[position])
    }

    override fun getItemCount(): Int = requestlist.size


}