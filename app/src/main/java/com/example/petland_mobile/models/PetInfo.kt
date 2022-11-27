package com.example.petland_mobile.models

import java.io.Serializable

class PetInfo : Serializable {
    var user : User? = null
    var pet : Pet? = null

    constructor(pet : Pet, user : User) {
        this.user = user
        this.pet = pet
    }
}