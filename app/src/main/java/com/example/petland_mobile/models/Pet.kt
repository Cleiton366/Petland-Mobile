package com.example.petland_mobile.models

var petList = mutableListOf<Pet>()

val PET_EXTRA = "petExtra"

class Pet : java.io.Serializable {
    var donatorInfo : User? = null
    lateinit var petid : String
    lateinit var donatorid : String
    lateinit var ownerid : String
    lateinit var petname : String
    lateinit var petphoto : String
    lateinit var city : String
    lateinit var sstate : String
    lateinit var address: String
    lateinit var age : String
    lateinit var medicalcondition : String
    lateinit var pettype : String
    var isadopted : Boolean = false
}