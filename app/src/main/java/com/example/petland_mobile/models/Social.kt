package com.example.petland_mobile.models

class Social {
    var followerUserId : String
    var followerUsername : String
    var followerUserAvatarurl : String
    var followingUserId : String
    var followingUsername : String
    var followingUserAvatarurl : String

    constructor(
        followerUserId: String,
        followerUsername: String,
        followerUserAvatarurl: String,
        followingUserId: String,
        followingUsername: String,
        followingUserAvatarurl: String
    ) {
        this.followerUserId = followerUserId
        this.followerUsername = followerUsername
        this.followerUserAvatarurl = followerUserAvatarurl
        this.followingUserId = followingUserId
        this.followingUsername = followingUsername
        this.followingUserAvatarurl = followingUserAvatarurl
    }
}