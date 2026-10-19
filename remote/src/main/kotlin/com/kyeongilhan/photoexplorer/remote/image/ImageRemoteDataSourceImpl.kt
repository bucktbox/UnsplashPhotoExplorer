package com.kyeongilhan.photoexplorer.remote.image

import com.kyeongilhan.photoexplorer.domain.di.IoDispatcher
import com.kyeongilhan.photoexplorer.data.photo.datasource.ImageRemoteDataSource
import com.kyeongilhan.photoexplorer.remote.di.ImageHttpClient
import com.kyeongilhan.photoexplorer.remote.api.apiCallUnsplash
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ImageRemoteDataSourceImpl @Inject constructor(
    @param:ImageHttpClient private val okHttpClient: OkHttpClient,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ImageRemoteDataSource {
    override suspend fun downloadImage(url: String): ByteArray = apiCallUnsplash {
        withContext(ioDispatcher) {
            val request = Request.Builder().url(url).get().build()
            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Download Failed ${response.code}")
                response.body?.bytes() ?: throw IOException("Download Failed : Empty Body")
            }
        }
    }
}