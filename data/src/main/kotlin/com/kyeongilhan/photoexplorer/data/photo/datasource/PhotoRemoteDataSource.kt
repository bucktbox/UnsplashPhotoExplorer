package com.kyeongilhan.photoexplorer.data.photo.datasource

import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoDetailEntity
import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoEntity

interface PhotoRemoteDataSource {
    // 최신 사진 가져오기
    suspend fun getLatestPhotos(page: Int, perPage: Int): List<PhotoEntity>

    // 사진 상세 조회
    suspend fun getPhotoDetail(photoId: String): PhotoDetailEntity

    // 한 페이지 당 30개로 설정
    companion object {
        const val MAX_PER_PAGE = 30
    }
}