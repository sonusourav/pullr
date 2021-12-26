package com.sonusourav.pullr.domain

import com.sonusourav.pullr.data.models.Repo
import com.sonusourav.pullr.data.services.ReposService
import io.reactivex.Single

/**
 * Class that connects the Data layer to Presentation, where the API objects are manipulated and observed by
 * the Views (Activity, Fragment or View)
 */
class ReposManager {
    private var reposService: ReposService = ReposService()

    fun getListOfRepos(user: String, page: Int, perPage: Int): Single<List<Repo>> {
        return reposService.getReposForUser(user, page, perPage)
            // By calling `onErrorResumeNext` we could apply our own error handling function
            .onErrorResumeNext { throwable -> Single.error(throwable) }
            // Since we are using Retrofit's Response, we will need to parse it and check
            // if the response was successful or not
            .flatMap { response ->
                if (!response.isSuccessful) {
                    Single.error(Throwable(response.code().toString()))
                } else Single.just(response)
            }
            // If the response is successful, we retrieve its body
            .map { response -> response.body() }
    }
}