package com.example.petland_mobile.models

import java.io.Serializable

class Social : Serializable{
    var follower_user_id : String
    var follower_username : String
    var follower_user_avatarurl : String
    var following_user_id : String
    var following_username : String
    var following_user_avatarurl : String

    constructor(
        followerUserId: String,
        followerUsername: String,
        followerUserAvatarurl: String,
        followingUserId: String,
        followingUsername: String,
        followingUserAvatarurl: String
    ) {
        this.follower_user_id = followerUserId
        this.follower_username = followerUsername
        this.follower_user_avatarurl = followerUserAvatarurl
        this.following_user_id = followingUserId
        this.following_username = followingUsername
        this.following_user_avatarurl = followingUserAvatarurl
    }
}