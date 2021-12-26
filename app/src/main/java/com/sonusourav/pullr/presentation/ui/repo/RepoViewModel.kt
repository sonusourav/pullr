package com.sonusourav.pullr.presentation.ui.repo

import com.sonusourav.pullr.data.models.Repo
import com.sonusourav.pullr.presentation.ui.base.BasePaginationViewModel
import com.sonusourav.pullr.utils.pagination.factory.ReposDataSourceFactory

class RepoViewModel : BasePaginationViewModel<ReposDataSourceFactory, Repo>() {
    init {
        dataSourceFactory = ReposDataSourceFactory(getListener(), null)
    }

    override fun getPageSize(): Int = 3

    /**
     * Handles a new user search
     */
    fun newSearch(user : String) {
        dataSourceFactory.user = user
        clearData()
    }
}