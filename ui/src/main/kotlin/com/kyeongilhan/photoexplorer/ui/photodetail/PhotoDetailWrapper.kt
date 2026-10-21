package com.kyeongilhan.photoexplorer.ui.photodetail

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
import com.kyeongilhan.photoexplorer.presentation.photodetail.PhotoDetailEffect
import com.kyeongilhan.photoexplorer.presentation.photodetail.PhotoDetailIntent
import com.kyeongilhan.photoexplorer.presentation.photodetail.PhotoDetailViewModel
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.common.showShortSnackbar
import com.kyeongilhan.photoexplorer.ui.common.toErrorMessageRes

@Composable
fun PhotoDetailWrapper(
    onBackClick: () -> Unit,
    viewModel: PhotoDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is PhotoDetailEffect.ShowError ->
                        snackbarHostState.showShortSnackbar(context.getString(effect.throwable.toErrorMessageRes()))

                    is PhotoDetailEffect.ShowSuccess -> {
                        when(effect.isFavorite) {
                            true -> snackbarHostState.showShortSnackbar(context.getString(R.string.favorite_added))
                            false -> snackbarHostState.showShortSnackbar(context.getString(R.string.favorite_removed))
                        }
                    }
                }
            }
        }
    }

    PhotoDetailScreen(
        state = state,
        onBackClick = onBackClick,
        onFavoriteClick = { viewModel.onIntent(PhotoDetailIntent.ClickFavorite) },
        onRetry = { viewModel.onIntent(PhotoDetailIntent.Retry) },
        snackBarHostState = snackbarHostState
    )
}