package com.kyeongilhan.photoexplorer.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

// 계속 값 오는데 param 없는거 (observable)
abstract class BaseNoParamsFlowUseCase<R>(
    private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<R> = execute().flowOn(dispatcher)

    protected abstract fun execute(): Flow<R>
}