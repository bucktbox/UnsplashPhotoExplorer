package com.kyeongilhan.photoexplorer.domain.favorite.model

import com.kyeongilhan.photoexplorer.domain.photo.model.Photo

// 좋아요 한 사진들 저장하는 데이터 클래스
data class FavoritePhoto(
    val photo: Photo,
    val localImagePath: String?,
    val savedAt: Long,
)