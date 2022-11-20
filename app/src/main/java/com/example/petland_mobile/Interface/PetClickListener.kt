package com.example.petland_mobile.Interface

import com.example.petland_mobile.models.Pet

interface PetClickListener{
    fun onClick(pet: Pet)
}