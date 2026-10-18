package com.kyeongilhan.photoexplorer.data.favorite.mapper

import com.kyeongilhan.photoexplorer.data.favorite.entity.FavoritePhotoEntity
import com.kyeongilhan.photoexplorer.data.photo.mapper.toDomain
import com.kyeongilhan.photoexplorer.data.photo.mapper.toEntity
import com.kyeongilhan.photoexplorer.domain.favorite.model.FavoritePhoto
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo

// favorite photo entity to domain
internal fun FavoritePhotoEntity.toDomain(): FavoritePhoto = FavoritePhoto(
    photo = photo.toDomain(),
    localImagePath = localImagePath,
    savedAt = savedAt,
)

// favorite photo domain to entity
internal fun Photo.toFavoriteEntity(savedAt: Long): FavoritePhotoEntity = FavoritePhotoEntity(
    photo = toEntity(),
    localImagePath = null,
    savedAt = savedAt,
)