package com.kyeongilhan.photoexplorer.remote.photo.dto

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class PhotoLatestDto(
    val id: String,
    val width: Int = 0,
    val height: Int = 0,
    val description: String? = null,
    @SerialName("alt_description") val altDescription: String? = null,
    val color: String? = null,
    val urls: UrlsDto,
    val user: UserDto,
    val links: LinksDto,
)

@InternalSerializationApi
@Serializable
data class UrlsDto(
    val regular: String,
)

@InternalSerializationApi
@Serializable
data class UserDto(
    val username: String,
    val name: String? = null,
    @SerialName("profile_image") val profileImage: ProfileImageDto? = null,
)

@InternalSerializationApi
@Serializable
data class ProfileImageDto(
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
)

@InternalSerializationApi
@Serializable
data class LinksDto(
    @SerialName("download_location") val downloadLocation: String,
)