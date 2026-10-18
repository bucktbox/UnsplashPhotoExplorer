package com.kyeongilhan.photoexplorer.data.favorite.datasource

import com.kyeongilhan.photoexplorer.data.favorite.entity.FavoritePhotoEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {
    // 좋아요 목록 가져오기
    fun observeFavorites(): Flow<List<FavoritePhotoEntity>>

    // 좋아요 사진 ID 목록 가져오기 (리스트에 반영하기 위함)
    fun observeFavoriteIds(): Flow<List<String>>

    // 좋아요 사진 단일 조회
    fun observeFavorite(photoId: String): Flow<FavoritePhotoEntity?>

    // 좋아요 저장하기
    suspend fun insertFavorite(entity: FavoritePhotoEntity)

    // 좋아요 삭제
    suspend fun deleteFavorite(photoId: String)

    // 좋아요 사진 단일 조회
    suspend fun getFavorite(photoId: String): FavoritePhotoEntity?

    // 다운로드 api 호출 성공 표시 (isDownloadCalled = true)
    suspend fun markDownloadCalled(photoId: String)

    // 파일 경로 업데이트 (이미지 다운로드 후)
    suspend fun updateLocalImagePath(photoId: String, path: String): Boolean
}