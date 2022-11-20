package com.example.petland_mobile.models

class UserFriendlist {
    var followingQtd: Int
    var followersQtd: Int
    var followers: MutableList<User>
    var following: MutableList<User>

    constructor(
        followingQtd: Int,
        followersQtd: Int,
        followers: MutableList<User>,
        following: MutableList<User>
    ) {
        this.followingQtd = followingQtd
        this.followersQtd = followersQtd
        this.followers = followers
        this.following = following
    }
}