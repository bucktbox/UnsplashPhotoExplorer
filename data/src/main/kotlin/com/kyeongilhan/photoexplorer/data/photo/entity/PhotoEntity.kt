package com.kyeongilhan.photoexplorer.data.photo.entity

data class PhotoEntity(
    val id: String,
    val width: Int,
    val height: Int,
    val description: String?,
    val color: String?,
    val imageUrl: String,
    val authorName: String,
    val authorUsername: String,
    val authorProfileImageUrl: String?,
    val downloadLocation: String,
)

data class PhotoDetailEntity(
    val photo: PhotoEntity,
    val views: Long?,
    val downloads: Long?,
    val likes: Int?,
    val exifName: String?,
    val location: String?,
    val tags: List<String>,
)