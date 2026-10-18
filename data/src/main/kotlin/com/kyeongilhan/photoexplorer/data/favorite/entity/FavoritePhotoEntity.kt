package com.kyeongilhan.photoexplorer.data.favorite.entity

import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoEntity

data class FavoritePhotoEntity(
    val photo: PhotoEntity,
    val localImagePath: String?,
    val savedAt: Long,
)