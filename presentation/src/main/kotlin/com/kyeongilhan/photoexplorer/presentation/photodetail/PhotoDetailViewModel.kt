package com.kyeongilhan.photoexplorer.presentation.photodetail

import androidx.lifecycle.SavedStateHandle
import com.kyeongilhan.photoexplorer.domain.favorite.usecase.SetFavoriteUseCase
import com.kyeongilhan.photoexplorer.domain.photo.usecase.GetPhotoDetailUseCase
import com.kyeongilhan.photoexplorer.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPhotoDetail: GetPhotoDetailUseCase,
    private val setFavorite: SetFavoriteUseCase,
) : BaseViewModel() {

    // PhotoDetailScreen에 전달되는 photoId
    private val photoId: String = checkNotNull(savedStateHandle["photoId"])

    private val _state = MutableStateFlow(PhotoDetailState())
    val state: StateFlow<PhotoDetailState> = _state.asStateFlow()

    private val _effect = Channel<PhotoDetailEffect>(Channel.BUFFERED)
    val effect: Flow<PhotoDetailEffect> = _effect.receiveAsFlow()

    private var detailJob: Job? = null

    init {
        loadDetail()
    }

    fun onIntent(intent: PhotoDetailIntent) {
        when (intent) {
            PhotoDetailIntent.ClickFavorite -> clickFavorite()
            PhotoDetailIntent.Retry -> loadDetail()
        }
    }

    private fun loadDetail() {
        // 중복 호출 안되게 Job으로 관리한다
        detailJob?.cancel()
        detailJob = getPhotoDetail(GetPhotoDetailUseCase.Params(photoId)).collectDataResource(
            onLoading = { _state.update { it.copy(isLoading = true, error = null) } },
            onSuccess = { detail ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        detail = detail,
                        error = null
                    )
                }
            },
            onError = { e -> _state.update { it.copy(isLoading = false, error = e) } },
        )
    }

    private fun clickFavorite() {
        val detail = _state.value.detail ?: return
        // 하트 표시는 UseCase의 Room 구독으로 자동으로 바뀜
        setFavorite(
            SetFavoriteUseCase.Params(
                photo = detail.photo,
                isFavorite = !detail.isFavorite
            )
        ).collectDataResource(
            onSuccess = { _effect.send(PhotoDetailEffect.ShowSuccess(!detail.isFavorite))},
            onError = { _effect.send(PhotoDetailEffect.ShowError(it)) }
        )
    }
}