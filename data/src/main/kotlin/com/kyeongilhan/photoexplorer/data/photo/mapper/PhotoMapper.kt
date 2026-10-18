package com.kyeongilhan.photoexplorer.data.photo.mapper

import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoDetailEntity
import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoEntity
import com.kyeongilhan.photoexplorer.domain.photo.model.Author
import com.kyeongilhan.photoexplorer.domain.photo.model.Exif
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import com.kyeongilhan.photoexplorer.domain.photo.model.PhotoDetail
import com.kyeongilhan.photoexplorer.domain.photo.model.PhotoStats

// photo entity to domain
internal fun PhotoEntity.toDomain(): Photo = Photo(
    id = id,
    width = width,
    height = height,
    description = description,
    color = color,
    imageUrl = imageUrl,
    author = Author(
        name = authorName,
        username = authorUsername,
        profileImageUrl = authorProfileImageUrl,
    ),
    downloadLocation = downloadLocation,
)

// photo detail entity to domain
internal fun PhotoDetailEntity.toDomain(): PhotoDetail = PhotoDetail(
    photo = photo.toDomain(),
    stats = PhotoStats(
        views = views,
        downloads = downloads,
        likes = likes,
        exif = exifName?.let { Exif(name = it) },
        location = location,
        tags = tags,
    ),
)

//photo domain to entity
internal fun Photo.toEntity(): PhotoEntity = PhotoEntity(
    id = id,
    width = width,
    height = height,
    description = description,
    color = color,
    imageUrl = imageUrl,
    authorName = author.name,
    authorUsername = author.username,
    authorProfileImageUrl = author.profileImageUrl,
    downloadLocation = downloadLocation,
)