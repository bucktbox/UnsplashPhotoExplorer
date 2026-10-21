package com.kyeongilhan.photoexplorer.ui.favorites

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
import com.kyeongilhan.photoexplorer.presentation.favorites.FavoritesEffect
import com.kyeongilhan.photoexplorer.presentation.favorites.FavoritesIntent
import com.kyeongilhan.photoexplorer.presentation.favorites.FavoritesViewModel
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.common.showShortSnackbar
import com.kyeongilhan.photoexplorer.ui.common.toErrorMessageRes

@Composable
fun FavoritesWrapper(
    onBackClick: () -> Unit,
    onPhotoClick: (String) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is FavoritesEffect.ShowSuccess -> {
                        // 성공 Snackbar
                        snackbarHostState.showShortSnackbar(context.getString(R.string.favorite_removed))
                    }
                    is FavoritesEffect.ShowError -> {
                        // 에러 Snackbar
                        snackbarHostState.showShortSnackbar(context.getString(effect.throwable.toErrorMessageRes()))
                    }

                }
            }
        }
    }

    FavoritesScreen(
        state = state,
        onBackClick = onBackClick,
        onPhotoClick = onPhotoClick,
        onRemoveClick = { photo -> viewModel.onIntent(FavoritesIntent.Remove(photo)) },
        snackbarHostState = snackbarHostState
    )
}