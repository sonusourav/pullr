package com.sonusourav.pullr.utils.pagination.datasource.base

/**
 * Interface that will let us communicate between
 * @see BaseDataSource class
 * and
 * @see com.sonusourav.pullr.presentation.ui.base.BasePaginationViewModel
 */
interface OnDataSourceLoading {
    fun onFirstFetch()
    fun onFirstFetchEndWithData()
    fun onFirstFetchEndWithoutData()
    fun onDataLoading()
    fun onDataLoadingEnd()
    fun onInitialError()
    fun onError()
}