package com.sonusourav.pullr.presentation.ui.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.sonusourav.pullr.presentation.utils.Event
import com.sonusourav.pullr.utils.pagination.datasource.base.OnDataSourceLoading
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base ViewModel class with observables and helper methods needed to use in the Pagination Library
 */
abstract class BasePaginationViewModel<T : DataSource.Factory<Int, K>, K> : ViewModel() {
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    protected lateinit var dataSourceFactory : T
    private var pagedObservable: Observable<PagedList<K>>? = null
    /**
     * Events exposed so that the Activity/Fragment can get data from the ViewModel regarding the [BaseDataSource] actions
     * [clearDataEvents] acts as an "event", rather than state. [Observer]s
     * are notified of the change as usual with [LiveData], but only one [Observer]
     * will actually read the data. For more information, check the [Event] class.
     */
    val clearDataEvents : MutableLiveData<Event<Unit>> get() = _clearDataEvents
    private val _clearDataEvents = MutableLiveData<Event<Unit>>()

    val emptyVisibilityEvents : MutableLiveData<Event<Boolean>> get() = _emptyVisibilityEvents
    private val _emptyVisibilityEvents = MutableLiveData<Event<Boolean>>()

    val recyclerViewLoadingEvents : MutableLiveData<Event<Boolean>> get() = _recyclerViewLoadingEvents
    private val _recyclerViewLoadingEvents = MutableLiveData<Event<Boolean>>()

    val errorEvent : MutableLiveData<Event<Unit>> get() = _errorToastEvent
    private val _errorToastEvent = MutableLiveData<Event<Unit>>()

    /**
     * This stipulates how many items are going to be fetched each time the user scrolls to the end of the recyclerview
     * Please note that initially 3 x [getPageSize] items are going to be loaded
     */
    abstract fun getPageSize() : Int

    //region Pagination
    fun clearData() {
        this.clearDataEvents.postValue(Event(Unit))
    }

    /**
     * Generates a new datasource,
     * Used when we need to do a new search for a different user
     */
    fun clearDataSource() {
        dataSourceFactory.create()
        createPagedObservable()
    }

    fun getItems(): Observable<PagedList<K>>? {
        if (pagedObservable == null) {
            createPagedObservable()
        }
        return pagedObservable
    }

    /**
     * Creates observable stream for the data fetched by the DataSource
     */
    private fun createPagedObservable() {
        pagedObservable = RxPagedListBuilder(
                dataSourceFactory,
                PagedList.Config.Builder()
                        .setPageSize(getPageSize())
                        .setEnablePlaceholders(false)
                        .build())
                .buildObservable()
    }

    /**
     * Listener used in the DataSource that we use to manipulate the Activity/Fragment to show/hide views and present
     * relevant information
     */
    protected fun getListener(): OnDataSourceLoading {
        return object : OnDataSourceLoading {
            override fun onFirstFetch() {
                Log.d("sonusouravdx001","inside onFirst fetch")
                showRecyclerLoading()
            }

            override fun onFirstFetchEndWithData() {
                Log.d("sonusouravdx001","inside onFirstFetchEndWithData")
                hideRecyclerLoading()
                hideEmptyVisibility()
            }

            override fun onFirstFetchEndWithoutData() {
                Log.d("sonusouravdx001","inside onFirstFetchEndWithoutData")
                hideRecyclerLoading()
                showEmptyVisibility()
            }

            override fun onDataLoading() {
                Log.d("sonusouravdx001","inside onDataLoading")
                showRecyclerLoading()
            }

            override fun onDataLoadingEnd() {
                Log.d("sonusouravdx001","inside onDataLoadingEnd")
                hideRecyclerLoading()
            }

            override fun onInitialError() {
                Log.d("sonusouravdx001","inside onInitialError")
                hideRecyclerLoading()
                showErrorToast()
            }

            override fun onError() {
                Log.d("sonusouravdx001","inside onError")
                hideRecyclerLoading()
                showErrorToast()
            }
        }
    }

    /**
     * Helper methods to show and hide views on the Activity/Fragment
     */
    fun showEmptyVisibility() {
        emptyVisibilityEvents.postValue(Event(true))
    }

    fun hideEmptyVisibility() {
        emptyVisibilityEvents.postValue(Event(false))
    }

    fun showRecyclerLoading() {
        recyclerViewLoadingEvents.postValue(Event(true))
    }

    fun hideRecyclerLoading() {
        recyclerViewLoadingEvents.postValue(Event(false))
    }

    fun showErrorToast() {
        errorEvent.postValue(Event(Unit))
    }

    fun addDisposable(d : Disposable) = compositeDisposable.add(d)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}