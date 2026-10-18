package com.kyeongilhan.photoexplorer.remote.dto.photo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDownloadDto(
    @SerialName("url")
    val url: String
)