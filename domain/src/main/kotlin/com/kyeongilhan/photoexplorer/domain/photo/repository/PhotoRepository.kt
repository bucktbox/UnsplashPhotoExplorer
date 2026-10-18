package com.kyeongilhan.photoexplorer.domain.photo.repository

import androidx.paging.PagingData
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import com.kyeongilhan.photoexplorer.domain.photo.model.PhotoDetail
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    // 최신 사진 가져오기 (페이징)
    fun getLatestPhotos(): Flow<PagingData<Photo>>

    // 사진 상세
    suspend fun getPhotoDetail(photoId: String): PhotoDetail
}