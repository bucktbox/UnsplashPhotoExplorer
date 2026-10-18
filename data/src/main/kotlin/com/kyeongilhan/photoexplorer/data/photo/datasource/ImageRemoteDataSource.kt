package com.kyeongilhan.photoexplorer.data.photo.datasource

interface ImageRemoteDataSource {
    // 이미지 파일 다운로드 (좋아요 후에)
    suspend fun downloadImage(url: String): ByteArray
}