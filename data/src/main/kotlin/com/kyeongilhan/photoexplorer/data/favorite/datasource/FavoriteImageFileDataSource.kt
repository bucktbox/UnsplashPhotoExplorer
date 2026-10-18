package com.kyeongilhan.photoexplorer.data.favorite.datasource

interface FavoriteImageFileDataSource {
    // 이미지 파일 저장하고 경로 반환
    suspend fun save(photoId: String, bytes: ByteArray): String

    // 이미지 파일 삭제
    suspend fun delete(photoId: String)
}