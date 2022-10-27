package com.example.petland_mobile.models

class PetAddress {
    private lateinit var city : String
    private lateinit var state : String

    constructor(city: String, state: String) {
        this.city = city
        this.state = state
    }

    override fun toString(): String {
        return "'petAddress':{'city':'$city', 'state':'$state'}"
    }
}