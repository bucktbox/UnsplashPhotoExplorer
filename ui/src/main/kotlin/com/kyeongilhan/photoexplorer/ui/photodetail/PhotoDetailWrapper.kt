package com.kyeongilhan.photoexplorer.ui.photodetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyeongilhan.photoexplorer.presentation.photodetail.PhotoDetailIntent
import com.kyeongilhan.photoexplorer.presentation.photodetail.PhotoDetailViewModel

@Composable
fun PhotoDetailWrapper(
    onBackClick: () -> Unit,
    viewModel: PhotoDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PhotoDetailScreen(
        state = state,
        onBackClick = onBackClick,
        onFavoriteClick = { viewModel.onIntent(PhotoDetailIntent.ClickFavorite) },
        onRetry = { viewModel.onIntent(PhotoDetailIntent.Retry) },
    )
}