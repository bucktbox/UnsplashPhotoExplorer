package com.kyeongilhan.photoexplorer.ui.photolist

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.kyeongilhan.photoexplorer.presentation.photolist.PhotoListEffect
import com.kyeongilhan.photoexplorer.presentation.photolist.PhotoListIntent
import com.kyeongilhan.photoexplorer.presentation.photolist.PhotoListViewModel
import com.kyeongilhan.photoexplorer.ui.common.toErrorMessageRes

@Composable
fun PhotoListWrapper(
    onPhotoClick: (String) -> Unit,
    onFavoritesClick: () -> Unit,
    viewModel: PhotoListViewModel = hiltViewModel(),
) {
    val photos = viewModel.photos.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // ViewEffect 처리
    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is PhotoListEffect.ShowError ->
                        snackbarHostState.showSnackbar(context.getString(effect.throwable.toErrorMessageRes()))
                }
            }
        }
    }

    // 화면에 photo list paging data 넘기기
    PhotoListScreen(
        state = state,
        photos = photos,
        snackbarHostState = snackbarHostState,
        onPhotoClick = onPhotoClick,
        onFavoriteClick = { photo, isFavorite ->
            viewModel.onIntent(PhotoListIntent.ClickFavorite(photo = photo, isFavorite = isFavorite))
        },
        onFavoritesClick = onFavoritesClick,
    )
}