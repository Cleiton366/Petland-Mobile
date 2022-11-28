package com.example.petland_mobile.models

import java.io.Serializable

class UserFriendlist : Serializable {
    var followingQtd: Int
    var followersQtd: Int
    var followers: MutableList<Social>
    var following: MutableList<Social>

    constructor(
        followingQtd: Int,
        followersQtd: Int,
        followers: MutableList<Social>,
        following: MutableList<Social>
    ) {
        this.followingQtd = followingQtd
        this.followersQtd = followersQtd
        this.followers = followers
        this.following = following
    }
}