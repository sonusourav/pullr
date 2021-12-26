package com.sonusourav.pullr.domain

import com.sonusourav.pullr.data.models.PullRequest
import com.sonusourav.pullr.data.services.PRService
import io.reactivex.Single

/**
 * Class that connects the Data layer to Presentation, where the API objects are manipulated and observed by
 * the Views (Activity, Fragment or View)
 */
class PRManager {
    var reposService: PRService = PRService()

    fun getClosedPRList(
        owner: String,
        repo: String,
        page: Int,
        perPage: Int,
        state: String
    ): Single<List<PullRequest>> {
        return reposService.getClosedPRs(owner, repo, page, perPage, state)
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