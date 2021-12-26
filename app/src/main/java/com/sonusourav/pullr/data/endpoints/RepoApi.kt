package com.sonusourav.pullr.data.endpoints

import com.sonusourav.pullr.data.models.Repo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoApi {

    @GET("users/{userId}/repos")
    fun getRepos(
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<Response<List<Repo>>>
}