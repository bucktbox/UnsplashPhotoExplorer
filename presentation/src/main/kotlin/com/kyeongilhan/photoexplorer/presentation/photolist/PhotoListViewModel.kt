package com.kyeongilhan.photoexplorer.presentation.photolist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kyeongilhan.photoexplorer.domain.favorite.usecase.ObserveFavoriteIdsUseCase
import com.kyeongilhan.photoexplorer.domain.favorite.usecase.SetFavoriteUseCase
import com.kyeongilhan.photoexplorer.domain.photo.usecase.GetLatestPhotoPagingUseCase
import com.kyeongilhan.photoexplorer.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    getLatestPhotoPaging: GetLatestPhotoPagingUseCase,
    observeFavoriteIds: ObserveFavoriteIdsUseCase,
    private val setFavorite: SetFavoriteUseCase,
) : BaseViewModel() {

    // 서버 목록에 좋아요 id를 합쳐서 칸마다 하트 상태를 붙임
    val photos: Flow<PagingData<PhotoCardUiModel>> = combine(
        // 하트가 바뀌어도 서버 목록을 다시 받지 않게 먼저 캐시
        getLatestPhotoPaging().cachedIn(viewModelScope),
        observeFavoriteIds(),
    ) { paging, favoriteIds ->
        paging.map { photo -> PhotoCardUiModel(photo = photo, isFavorite = photo.id in favoriteIds) }
    }

    // 상단 배지 숫자
    val state: StateFlow<PhotoListState> = observeFavoriteIds()
        .map { favoriteIds -> PhotoListState(favoriteCount = favoriteIds.size) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PhotoListState())

    // 화면에 보낼 이벤트
    private val _effect = Channel<PhotoListEffect>(Channel.BUFFERED)
    val effect: Flow<PhotoListEffect> = _effect.receiveAsFlow()

    // 화면에서 온 인텐트 처리
    fun onIntent(intent: PhotoListIntent) {
        when (intent) {
            is PhotoListIntent.ClickFavorite -> clickFavorite(intent)
        }
    }

    private fun clickFavorite(intent: PhotoListIntent.ClickFavorite) {
        // 하트 표시는 Room 구독(observeFavoriteIds)으로 자동으로 바뀜. 실패만 알려 줌
        setFavorite(SetFavoriteUseCase.Params(photo = intent.photo, isFavorite = intent.isFavorite))
            .collectDataResource(
                onSuccess = { _effect.send(PhotoListEffect.ShowSuccess(isFavorite = intent.isFavorite))},
                onError = { _effect.send(PhotoListEffect.ShowError(it)) },
            )
    }
}