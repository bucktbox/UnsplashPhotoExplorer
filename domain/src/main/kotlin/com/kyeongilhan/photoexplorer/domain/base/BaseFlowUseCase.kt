package com.kyeongilhan.photoexplorer.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

// 계속 값 오는 use case (observable)
abstract class BaseFlowUseCase<in P, R>(
    private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(params: P): Flow<R> = execute(params).flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<R>
}
