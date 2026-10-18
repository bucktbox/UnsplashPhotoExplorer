package com.kyeongilhan.photoexplorer.remote.api

import com.kyeongilhan.photoexplorer.data.photo.datasource.PhotoRemoteDataSource
import com.kyeongilhan.photoexplorer.remote.photo.dto.PhotoDetailDto
import com.kyeongilhan.photoexplorer.remote.photo.dto.PhotoDownloadDto
import com.kyeongilhan.photoexplorer.remote.photo.dto.PhotoLatestDto
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface UnsplashApiService {

    // 최신 사진 목록 받아오기 API
    @OptIn(InternalSerializationApi::class)
    @GET("photos")
    suspend fun getLatestPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PhotoRemoteDataSource.MAX_PER_PAGE,
        @Query("order_by") orderBy: String = ORDER_BY_LATEST,
    ): List<PhotoLatestDto>

    // 사진 상세 조회 API
    @OptIn(InternalSerializationApi::class)
    @GET("photos/{id}")
    suspend fun getPhotoDetail(@Path("id") photoId: String): PhotoDetailDto

    // 다운로드 전 호출 API
    @GET
    suspend fun callDownloadApi(@Url downloadLocation: String): PhotoDownloadDto

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        private const val ORDER_BY_LATEST = "latest"
    }
}