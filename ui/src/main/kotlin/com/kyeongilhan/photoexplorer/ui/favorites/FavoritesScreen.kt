package com.kyeongilhan.photoexplorer.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import com.kyeongilhan.photoexplorer.presentation.favorites.FavoritesState
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.common.CircularLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    state: FavoritesState,
    onBackClick: () -> Unit,
    onPhotoClick: (String) -> Unit,
    onRemoveClick: (Photo) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.favorites)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
        when {
            state.isLoading -> CircularLoading(contentModifier)
            // 데이터 없음
            state.favorites.isEmpty() -> Box(
                modifier = contentModifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(R.string.favorites_empty))
            }
            // 사진 목록 화면하고 동일한 구조
            else -> LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(minSize = 160.dp),
                modifier = contentModifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(items = state.favorites, key = { it.photo.id }) { favorite ->
                    FavoritePhotoCard(
                        favorite = favorite,
                        onClick = { onPhotoClick(favorite.photo.id) },
                        onRemoveClick = { onRemoveClick(favorite.photo) },
                    )
                }
            }
        }
    }
}