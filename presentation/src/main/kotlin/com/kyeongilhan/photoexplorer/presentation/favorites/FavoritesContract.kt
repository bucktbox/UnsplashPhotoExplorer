package com.kyeongilhan.photoexplorer.presentation.favorites

import com.kyeongilhan.photoexplorer.domain.favorite.model.FavoritePhoto
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo

data class FavoritesState(
    // 처음 Room 값이 오기 전 (빈 목록과 구분해서 깜빡임 방지)
    val isLoading: Boolean = true,
    val favorites: List<FavoritePhoto> = emptyList(),
)

sealed interface FavoritesIntent {
    // 좋아요 삭제
    data class Remove(val photo: Photo) : FavoritesIntent
}

sealed interface FavoritesEffect {
    data object ShowSuccess : FavoritesEffect
    data class ShowError(val throwable: Throwable) : FavoritesEffect
}