package com.kyeongilhan.photoexplorer.data.favorite.entity

import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoEntity

data class FavoritePhotoEntity(
    val photo: PhotoEntity,
    val localImagePath: String?,
    val savedAt: Long,
    // 다운로드 api 호출 했는지 체크 (중복 호출 방지)
    val isDownloadCalled: Boolean
)