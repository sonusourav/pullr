package com.sonusourav.pullr.data.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("avatar_url") var profileUrl: String?,
    @SerializedName("followers_url") var followerUrl: String?,
    @SerializedName("login") var prOwner: String?
)