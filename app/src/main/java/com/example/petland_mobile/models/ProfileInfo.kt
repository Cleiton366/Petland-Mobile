package com.example.petland_mobile.models

import java.io.Serializable

class ProfileInfo : Serializable {
    var loggedUser: User
    var user : User? = null
    var isVisitingOtherProfile : Boolean = false

    constructor(loggedUser : User) {
        this.loggedUser = loggedUser
    }
    constructor(loggedUser : User, user: User) {
        this.loggedUser = loggedUser
        this.user = user
        this.isVisitingOtherProfile = true
    }
}