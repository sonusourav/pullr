package com.sonusourav.pullr.utils.pagination.datasource

import android.annotation.SuppressLint
import com.sonusourav.pullr.data.models.PullRequest
import com.sonusourav.pullr.domain.PRManager
import com.sonusourav.pullr.utils.pagination.datasource.base.BaseDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Class that handles how to retrieve data for the recyclerview
 * @see BaseDataSource
 */
class PrDataSource(var user: String, var repo: String) : BaseDataSource<PullRequest>() {
    private val manager: PRManager = PRManager()

    @SuppressLint("CheckResult")
    override fun loadInitialData(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PullRequest>
    ) {
        // in the initial load, we will start at page 0, and retrieve the number of pages in the params.requestLoadSize
        manager.getClosedPRList(user, repo, 0, params.requestedLoadSize, "closed")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { items -> submitInitialData(items, params, callback) },
                { error -> submitInitialError(error) }
            )
    }

    @SuppressLint("CheckResult")
    override fun loadAdditionalData(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PullRequest>
    ) {
        manager.getClosedPRList(user, repo, params.key, params.requestedLoadSize, "closed")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { items -> submitData(items, params, callback) },
                { error -> submitError(error) }
            )
    }
}