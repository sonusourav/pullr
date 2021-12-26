package com.sonusourav.pullr.data.models

import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("title") var title: String?,
    @SerializedName("created_at") var createdDate: String?,
    @SerializedName("closed_at") var closedDate: String?,
    @SerializedName("user") var user: User?,
    @SerializedName("number") var pullRequestId: Int?,
    var userName: String? = null
)