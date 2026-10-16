package com.kyeongilhan.photoexplorer.dataresource

sealed class DataResource<T> {
    // Loading,Success,Error로 데이터 상태 일반화
    data class Loading<T>(val cachedData: T? = null) : DataResource<T>()
    data class Success<T>(val data: T) : DataResource<T>()
    data class Error<T>(val throwable: Throwable) : DataResource<T>()
}