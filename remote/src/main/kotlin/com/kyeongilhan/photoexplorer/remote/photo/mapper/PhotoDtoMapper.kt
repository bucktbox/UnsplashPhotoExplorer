package com.kyeongilhan.photoexplorer.remote.photo.mapper

import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoDetailEntity
import com.kyeongilhan.photoexplorer.data.photo.entity.PhotoEntity
import com.kyeongilhan.photoexplorer.remote.photo.dto.PhotoDetailDto
import com.kyeongilhan.photoexplorer.remote.photo.dto.PhotoLatestDto
import kotlinx.serialization.InternalSerializationApi


// api 데이터 -> entity로 변환해서 넘겨주기
@OptIn(InternalSerializationApi::class)
internal fun PhotoLatestDto.toEntity(): PhotoEntity = PhotoEntity(
    id = id,
    width = width,
    height = height,
    description = description ?: altDescription,
    color = color,
    imageUrl = urls.regular,
    authorName = user.name ?: user.username,
    authorUsername = user.username,
    authorProfileImageUrl = user.profileImage?.let{ it.large ?: it.medium ?: it.small },
    downloadLocation = links.downloadLocation,
)

@OptIn(InternalSerializationApi::class)
internal fun PhotoDetailDto.toEntity(): PhotoDetailEntity = PhotoDetailEntity(
    photo = PhotoEntity(
        id = id,
        width = width,
        height = height,
        description = description ?: altDescription,
        color = color,
        imageUrl = urls.regular,
        authorName = user.name ?: user.username,
        authorUsername = user.username,
        authorProfileImageUrl = user.profileImage?.let{ it.large ?: it.medium ?: it.small },
        downloadLocation = links.downloadLocation,
    ),
    views = views,
    downloads = downloads,
    likes = likes,
    exifName = exif?.name,
    location = location?.name,
    tags = tags.mapNotNull { it.title },
)