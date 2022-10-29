package com.example.petland_mobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.databinding.CardCellBinding
import com.example.petland_mobile.holders.CardViewHolder
import com.example.petland_mobile.models.Pet

class CardAdapter (private val pets : List<Pet>)
    : RecyclerView.Adapter<CardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindPet(pets[position])
    }

    override fun getItemCount(): Int = pets.size

}