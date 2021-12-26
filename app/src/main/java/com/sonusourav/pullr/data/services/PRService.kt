package com.sonusourav.pullr.data.services

import com.sonusourav.pullr.data.endpoints.PullRequestApi
import com.sonusourav.pullr.data.models.PullRequest
import com.sonusourav.pullr.utils.networking.NetworkTools
import io.reactivex.Single
import retrofit2.Response

class PRService {
    var api: PullRequestApi = NetworkTools.retrofit.create(PullRequestApi::class.java)

    fun getClosedPRs(
        owner: String,
        repo: String,
        page: Int,
        perPage: Int,
        state: String
    ): Single<Response<List<PullRequest>>> {
        return api.getClosedPRs(owner, repo, page, perPage, state)
    }
}