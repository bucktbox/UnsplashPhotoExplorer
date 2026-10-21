package com.kyeongilhan.photoexplorer.presentation.favorites

import androidx.lifecycle.viewModelScope
import com.kyeongilhan.photoexplorer.domain.favorite.usecase.ObserveFavoritesUseCase
import com.kyeongilhan.photoexplorer.domain.favorite.usecase.SetFavoriteUseCase
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import com.kyeongilhan.photoexplorer.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    observeFavorites: ObserveFavoritesUseCase,
    private val setFavorite: SetFavoriteUseCase,
) : BaseViewModel() {

    // Room 좋아요 목록 받아오기
    val state: StateFlow<FavoritesState> = observeFavorites()
        .map { favorites -> FavoritesState(isLoading = false, favorites = favorites) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), FavoritesState())

    private val _effect = Channel<FavoritesEffect>(Channel.BUFFERED)
    val effect: Flow<FavoritesEffect> = _effect.receiveAsFlow()

    fun onIntent(intent: FavoritesIntent) {
        when (intent) {
            is FavoritesIntent.Remove -> remove(intent.photo)
        }
    }

    private fun remove(photo: Photo) {
        setFavorite(SetFavoriteUseCase.Params(photo = photo, isFavorite = false)).collectDataResource(
            // 지운 다음에 성공 스낵바
            onSuccess = { _effect.send(FavoritesEffect.ShowSuccess) },
            onError = { _effect.send(FavoritesEffect.ShowError(it)) },
        )
    }
}