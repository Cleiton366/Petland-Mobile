package com.example.petland_mobile.models

class User {
    lateinit var id : String
    lateinit var username : String
    lateinit var email : String
    lateinit var avatarurl : String

    constructor(id : String, username : String
    , email : String, avatarurl : String) {
        this.id = id
        this.username = username
        this.email = email
        this.avatarurl = avatarurl
    }
}