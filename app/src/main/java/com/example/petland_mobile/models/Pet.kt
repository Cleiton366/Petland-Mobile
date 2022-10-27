package com.example.petland_mobile.models

import io.ktor.utils.io.core.*
import kotlin.properties.Delegates

class Pet {
    private lateinit var donatorId : String
    private lateinit var ownerId : String
    private lateinit var petName : String
    private var age : Int
    private lateinit var medicalCondition : String
    private lateinit var petType : String
    private lateinit var donatorInfo : User
    private lateinit var petAddress : PetAddress
    private lateinit var imageURL : String
    private lateinit var petId : String
    private var isAdopted by Delegates.notNull<Boolean>()

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