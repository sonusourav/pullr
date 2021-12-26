package com.sonusourav.pullr.data.endpoints

import com.sonusourav.pullr.data.models.PullRequest
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PullRequestApi {

    @GET("repos/{owner}/{repo}/pulls")
    fun getClosedPRs(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("state") state: String
    ): Single<Response<List<PullRequest>>>
}