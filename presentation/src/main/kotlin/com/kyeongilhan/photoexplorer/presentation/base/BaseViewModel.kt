package com.kyeongilhan.photoexplorer.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyeongilhan.photoexplorer.dataresource.DataResource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    // UseCase 결과(DataResource)를 받아서 상태별로 나눠 처리
    protected fun <T> Flow<DataResource<T>>.collectDataResource(
        onSuccess: suspend (T) -> Unit,
        onError: suspend (Throwable) -> Unit = {},
        onLoading: suspend () -> Unit = {},
    ): Job = viewModelScope.launch {
        collect { result ->
            when (result) {
                is DataResource.Loading -> onLoading()
                is DataResource.Success -> onSuccess(result.data)
                is DataResource.Error -> onError(result.throwable)
            }
        }
    }
}