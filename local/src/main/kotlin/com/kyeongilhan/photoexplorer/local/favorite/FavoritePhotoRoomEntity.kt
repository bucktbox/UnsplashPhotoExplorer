package com.kyeongilhan.photoexplorer.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FavoritePhotoRoomEntity.TABLE_NAME)
data class FavoritePhotoRoomEntity(
    @PrimaryKey val id: String,
    val width: Int,
    val height: Int,
    val description: String?,
    val color: String?,
    val imageUrl: String,
    val authorName: String,
    val authorUsername: String,
    val authorProfileImageUrl: String?,
    val downloadLocation: String,
    val downloadCalled: Boolean = false,
    val localImagePath: String? = null,
    val savedAt: Long,
) {
    companion object {
        const val TABLE_NAME = "favorite_photo"
    }
}