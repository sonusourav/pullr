package com.sonusourav.pullr.presentation.ui.pullRequest

import com.sonusourav.pullr.data.models.PullRequest
import com.sonusourav.pullr.presentation.ui.base.BasePaginationViewModel
import com.sonusourav.pullr.utils.pagination.factory.PullRequestDataSourceFactory

class PullRequestViewModel : BasePaginationViewModel<PullRequestDataSourceFactory, PullRequest>() {
    init {
        dataSourceFactory = PullRequestDataSourceFactory(getListener(), null, null)
    }

    override fun getPageSize(): Int = 10

    /**
     * Handles a new user search
     */
    fun fetchClosedPullRequest(user : String, repo: String) {
        dataSourceFactory.user = user
        dataSourceFactory.repo = repo
        clearData()
    }
}