package com.kyeongilhan.photoexplorer.dataresource

sealed class DomainException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {
    // 발생할 수 있는 에러 정의 (일단 네트워크, 인증, api 호출 횟수(50회), 404, 그 외)
    // 구현하다가 추가 발견하면 넣기
    class Network(cause: Throwable? = null) : DomainException("Network error", cause)
    class Auth(cause: Throwable? = null) : DomainException("Unauthorized", cause)
    class RateLimited(cause: Throwable? = null) : DomainException("Rate limited", cause)
    class NotFound(cause: Throwable? = null) : DomainException("Not found", cause)
    class Unknown(cause: Throwable? = null) : DomainException("Unknown error", cause)
}

// exception 을 domain exception 으로 변환하는 함수
fun Throwable.toDomainException(): DomainException = this as? DomainException ?: DomainException.Unknown(this)