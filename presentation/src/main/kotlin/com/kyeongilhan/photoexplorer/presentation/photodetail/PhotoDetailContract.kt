package com.kyeongilhan.photoexplorer.presentation.photodetail

import com.kyeongilhan.photoexplorer.domain.photo.model.PhotoDetailResult

data class PhotoDetailState(
    val isLoading: Boolean = true,
    val detail: PhotoDetailResult? = null,
    val error: Throwable? = null,
)

sealed interface PhotoDetailIntent {
    data object ClickFavorite : PhotoDetailIntent
    data object Retry : PhotoDetailIntent
}

sealed interface PhotoDetailEffect {
    data class ShowSuccess(val isFavorite: Boolean) : PhotoDetailEffect
    data class ShowError(val throwable: Throwable) : PhotoDetailEffect
}