package com.kyeongilhan.photoexplorer.remote.photo.dto

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class PhotoDetailDto(
    val id: String,
    val width: Int = 0,
    val height: Int = 0,
    val description: String? = null,
    @SerialName("alt_description") val altDescription: String? = null,
    val color: String? = null,
    val urls: UrlsDto,
    val user: UserDto,
    val links: LinksDto,
    val likes: Int? = null,
    val views: Long? = null,
    val downloads: Long? = null,
    val exif: ExifDto? = null,
    val location: LocationDto? = null,
    val tags: List<TagDto> = emptyList(),
)

@InternalSerializationApi
@Serializable
data class ExifDto(
    val name: String? = null,
)

@InternalSerializationApi
@Serializable
data class LocationDto(
    val name: String? = null,
)

@InternalSerializationApi
@Serializable
data class TagDto(
    val title: String? = null,
)