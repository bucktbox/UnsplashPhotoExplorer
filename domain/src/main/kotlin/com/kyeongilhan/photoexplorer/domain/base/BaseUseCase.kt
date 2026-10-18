package com.kyeongilhan.photoexplorer.domain.base

import com.kyeongilhan.photoexplorer.dataresource.DataResource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

// 단일 실행하는 UseCase (api call)
abstract class BaseUseCase<in P, R>(
    private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(params: P): Flow<DataResource<R>> = flow {
        // 첨에는 loading 을 보내준다
        emit(DataResource.Loading())
        val result = try {
            execute(params)
        } catch (e: CancellationException) {
            // 취소는 에러로 바꾸지 않음
            throw e
        } catch (e: Exception) {
            emit(DataResource.Error(e))
            return@flow
        }
        emit(DataResource.Success(result))
    }.flowOn(dispatcher)

    suspend fun run(params: P): R = withContext(dispatcher) {
        execute(params)
    }

    protected abstract suspend fun execute(params: P): R
}
