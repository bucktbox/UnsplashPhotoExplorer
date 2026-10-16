package com.kyeongilhan.photoexplorer.domain.favorite.repository

interface FavoriteImageRepository {
    // 좋아요 한 사진 이미지 파일 저장 (백그라운드에서 받음, 바로 리턴)
    fun saveFavoriteImage(photoId: String)
    // 로컬에 저장한 이미지 파일 삭제 (백그라운드에서 지움, 바로 리턴)
    fun removeFavoriteImage(photoId: String)
}