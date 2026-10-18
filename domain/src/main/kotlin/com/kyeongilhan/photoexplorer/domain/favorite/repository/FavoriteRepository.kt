package com.kyeongilhan.photoexplorer.domain.favorite.repository

import com.kyeongilhan.photoexplorer.domain.favorite.model.FavoritePhoto
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    // 좋아요 한 사진 ID 목록 (사진 목록 화면에 좋아요 칠하기)
    fun observeFavoriteIds(): Flow<Set<String>>
    // 좋아요 한 사진 목록
    fun observeFavorites(): Flow<List<FavoritePhoto>>
    // 좋아요 한 사진 하나 가져오기
    fun observeFavorite(photoId: String): Flow<FavoritePhoto?>
    // 좋아요 (사진 url 등 정보 필요할 거 같아서 사진으로 넘김)
    suspend fun saveFavorite(photo: Photo)
    // 좋아요 해제
    suspend fun removeFavorite(photoId: String)
}