package com.sonusourav.pullr.data.models

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("name") var name: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("language") var language: String?,
    @SerializedName("html_url") var url: String?,
    @SerializedName("stargazers_count") var startCount: Int?,
    @SerializedName("watchers_count") var watchersCount: Int?
)