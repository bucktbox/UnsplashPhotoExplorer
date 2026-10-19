package com.kyeongilhan.photoexplorer.remote.api

import com.kyeongilhan.photoexplorer.dataresource.DomainException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

private const val HTTP_UNAUTHORIZED = 401
private const val HTTP_FORBIDDEN = 403
private const val HTTP_NOT_FOUND = 404

// 기본 base api call
internal suspend fun <T> apiCallUnsplash(block: suspend () -> T): T = try {
    block()
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    throw e.toUnsplashDomainException()
}

// api call 중 발생하는 에러를 domain exception으로 변환
internal fun Throwable.toUnsplashDomainException(): DomainException = when (this) {
    is DomainException -> this
    is IOException -> DomainException.Network(this)
    is HttpException -> when (code()) {
        HTTP_UNAUTHORIZED -> DomainException.Auth(this)
        // API Key 시간당 50번 제한 -> 403 error 줌
        HTTP_FORBIDDEN -> DomainException.RateLimited(this)
        HTTP_NOT_FOUND -> DomainException.NotFound(this)
        else -> DomainException.Unknown(this)
    }
    else -> DomainException.Unknown(this)
}