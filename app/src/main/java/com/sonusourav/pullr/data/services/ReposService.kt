package com.sonusourav.pullr.data.services

import com.sonusourav.pullr.data.endpoints.RepoApi
import com.sonusourav.pullr.data.models.Repo
import com.sonusourav.pullr.utils.networking.NetworkTools
import io.reactivex.Single
import retrofit2.Response

class ReposService {
    private var api: RepoApi = NetworkTools.retrofit.create(RepoApi::class.java)

    fun getReposForUser(user: String, page: Int, perPage: Int): Single<Response<List<Repo>>> {
        return api.getRepos(user, page, perPage)
    }
}