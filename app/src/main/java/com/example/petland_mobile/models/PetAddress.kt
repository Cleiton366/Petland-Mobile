package com.example.petland_mobile.models

class PetAddress : java.io.Serializable {
    private var city : String
    private var state : String
    private var address : String

    constructor(city: String, state: String, address : String) {
        this.city = city
        this.state = state
        this.address = address
    }

    override fun toString(): String {
        return "${address}+${city}+${state}"
    }
}