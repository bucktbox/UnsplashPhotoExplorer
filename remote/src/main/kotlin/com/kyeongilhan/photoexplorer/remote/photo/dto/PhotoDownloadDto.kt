package com.kyeongilhan.photoexplorer.remote.photo.dto


import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class PhotoDownloadDto(
    val url: String? = null,
)