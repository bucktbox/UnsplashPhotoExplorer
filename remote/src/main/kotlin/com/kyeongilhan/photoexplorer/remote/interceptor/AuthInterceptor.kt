package com.kyeongilhan.photoexplorer.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val accessKey: String,
    private val apiHost: String = API_HOST,
) : Interceptor {
    // retrofit api call 할때 헤더 넣는거
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.host != apiHost) return chain.proceed(request)

        val authorizedRequest = request.newBuilder()
            .header(HEADER_AUTHORIZATION, "Client-ID $accessKey")
            .header(HEADER_ACCEPT_VERSION, API_VERSION)
            .build()
        return chain.proceed(authorizedRequest)
    }

    companion object {
        const val API_HOST = "api.unsplash.com"
        const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_ACCEPT_VERSION = "Accept-Version"
        private const val API_VERSION = "v1"
    }
}