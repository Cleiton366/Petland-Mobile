package com.example.petland_mobile.models

import io.ktor.utils.io.core.*
import kotlin.properties.Delegates

class PetForm {
    var donatorId : String
    var petName : String
    var age : Int
    var medicalCondition : String
    var petType : String
    var petAddress : PetAddress
    lateinit var imageURL : String
    lateinit var petId : String
    var isAdopted by Delegates.notNull<Boolean>()

    constructor(
        donatorId: String,
        petName: String,
        age: Int,
        medicalCondition: String,
        petType: String,
        petAddress: PetAddress
    ) {
        this.donatorId = donatorId
        this.petName = petName
        this.age = age
        this.medicalCondition = medicalCondition
        this.petType = petType
        this.petAddress = petAddress
    }

    override fun toString(): String {
        return "'donatorId':'$donatorId', 'petName':'$petName', 'age':'$age', 'medicalCondition':'$medicalCondition', 'petType':'$petType', ${petAddress.toString()}"
    }

}