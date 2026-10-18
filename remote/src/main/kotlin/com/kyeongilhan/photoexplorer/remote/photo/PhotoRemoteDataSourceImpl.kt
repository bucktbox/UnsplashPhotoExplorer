package com.kyeongilhan.photoexplorer.remote.photo

import com.kyeongilhan.photoexplorer.data.photo.datasource.PhotoRemoteDataSource
import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoDetailEntity
import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoEntity
import com.kyeongilhan.photoexplorer.remote.api.UnsplashApiService
import com.kyeongilhan.photoexplorer.remote.api.apiCallUnsplash
import com.kyeongilhan.photoexplorer.remote.photo.mapper.toEntity
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PhotoRemoteDataSourceImpl @Inject constructor(
    private val unsplashApiService: UnsplashApiService,
) : PhotoRemoteDataSource {
    @OptIn(InternalSerializationApi::class)
    override suspend fun getLatestPhotos(page: Int, perPage: Int): List<PhotoEntity> = apiCallUnsplash {
        unsplashApiService.getLatestPhotos(page = page, perPage = perPage).map { it.toEntity() }
    }

    @OptIn(InternalSerializationApi::class)
    override suspend fun getPhotoDetail(photoId: String): PhotoDetailEntity = apiCallUnsplash {
        unsplashApiService.getPhotoDetail(photoId).toEntity()
    }

    override suspend fun callDownloadApi(downloadLocation: String) {
        apiCallUnsplash { unsplashApiService.callDownloadApi(downloadLocation) }
    }
}