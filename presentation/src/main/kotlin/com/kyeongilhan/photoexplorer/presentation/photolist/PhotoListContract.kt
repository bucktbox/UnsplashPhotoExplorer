package com.kyeongilhan.photoexplorer.presentation.photolist

import com.kyeongilhan.photoexplorer.domain.photo.model.Photo

// 목록 칸 하나 (사진 + 하트 상태)
data class PhotoCardUiModel(
    val photo: Photo,
    val isFavorite: Boolean,
)

// 화면 상태 (목록은 PagingData라 따로)
data class PhotoListState(
    val favoriteCount: Int = 0,
)

// 화면 -> viewModel Intent
sealed interface PhotoListIntent {
    // isFavorite: 누른 뒤에 될 상태 (true = 좋아요 하기)
    data class ClickFavorite(val photo: Photo, val isFavorite: Boolean) : PhotoListIntent
}

// viewModel -> 화면 단일 이벤트
sealed interface PhotoListEffect {
    data class ShowError(val throwable: Throwable) : PhotoListEffect
}