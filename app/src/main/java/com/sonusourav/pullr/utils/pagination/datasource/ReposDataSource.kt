package com.sonusourav.pullr.utils.pagination.datasource

import android.annotation.SuppressLint
import com.sonusourav.pullr.data.models.Repo
import com.sonusourav.pullr.domain.ReposManager
import com.sonusourav.pullr.utils.pagination.datasource.base.BaseDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Class that handles how to retrieve data for the recyclerview
 * @see BaseDataSource
 */
class ReposDataSource(var user: String) : BaseDataSource<Repo>() {
    private val manager: ReposManager = ReposManager()

    @SuppressLint("CheckResult")
    override fun loadInitialData(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repo>
    ) {
        // in the initial load, we will start at page 0, and retrieve the number of pages in the params.requestLoadSize
        manager.getListOfRepos(user, 0, params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { items -> submitInitialData(items, params, callback) },
                { error -> submitInitialError(error) }
            )
    }

    @SuppressLint("CheckResult")
    override fun loadAdditionalData(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        manager.getListOfRepos(user, params.key, params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { items -> submitData(items, params, callback) },
                { error -> submitError(error) }
            )
    }
}