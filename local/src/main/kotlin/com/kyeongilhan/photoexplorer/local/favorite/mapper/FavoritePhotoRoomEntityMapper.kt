package com.kyeongilhan.photoexplorer.local.favorite.mapper
import com.kyeongilhan.photoexplorer.data.favorite.entity.FavoritePhotoEntity
import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoEntity
import com.kyeongilhan.photoexplorer.local.favorite.room.FavoritePhotoRoomEntity

internal fun FavoritePhotoRoomEntity.toEntity(): FavoritePhotoEntity = FavoritePhotoEntity(
    photo = PhotoEntity(
        id = id,
        width = width,
        height = height,
        description = description,
        color = color,
        imageUrl = imageUrl,
        authorName = authorName,
        authorUsername = authorUsername,
        authorProfileImageUrl = authorProfileImageUrl,
        downloadLocation = downloadLocation,
    ),
    isDownloadCalled = downloadCalled,
    localImagePath = localImagePath,
    savedAt = savedAt,
)

internal fun FavoritePhotoEntity.toRoomEntity(): FavoritePhotoRoomEntity = FavoritePhotoRoomEntity(
    id = photo.id,
    width = photo.width,
    height = photo.height,
    description = photo.description,
    color = photo.color,
    imageUrl = photo.imageUrl,
    authorName = photo.authorName,
    authorUsername = photo.authorUsername,
    authorProfileImageUrl = photo.authorProfileImageUrl,
    downloadLocation = photo.downloadLocation,
    downloadCalled = isDownloadCalled,
    localImagePath = localImagePath,
    savedAt = savedAt,
)