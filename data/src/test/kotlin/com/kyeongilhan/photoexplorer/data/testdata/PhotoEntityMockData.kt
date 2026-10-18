package com.kyeongilhan.photoexplorer.data.testdata

import com.kyeongilhan.photoexplorer.data.favorite.entity.FavoritePhotoEntity
import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoEntity

// 테스트용 Photo Entity 데이터 (mockK 사용)
fun createPhotoEntity(id: String) = PhotoEntity(
    id = id,
    width = 300,
    height = 200,
    description = null,
    color = "#112233",
    imageUrl = "https://images.unsplash.com/photo-$id?w=1080",
    authorName = "User $id",
    authorUsername = "user_$id",
    authorProfileImageUrl = "https://images.unsplash.com/profile-$id",
    downloadLocation = "https://api.unsplash.com/photos/$id/download",
)

fun createFavoriteEntity(id: String) = FavoritePhotoEntity(
    photo = createPhotoEntity(id),
    localImagePath = null,
    savedAt = 0,
    isDownloadCalled = false
)