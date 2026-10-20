package com.kyeongilhan.photoexplorer.ui.photolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import com.kyeongilhan.photoexplorer.presentation.photolist.PhotoCardUiModel
import com.kyeongilhan.photoexplorer.presentation.photolist.PhotoListState
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.common.CircularLoading
import com.kyeongilhan.photoexplorer.ui.common.ErrorView
import com.kyeongilhan.photoexplorer.ui.photolist.screenitem.PhotoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoListScreen(
    state: PhotoListState,
    photos: LazyPagingItems<PhotoCardUiModel>,
    snackbarHostState: SnackbarHostState,
    onPhotoClick: (String) -> Unit,
    onFavoriteClick: (Photo, Boolean) -> Unit,
    onFavoritesClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.photo_list_title)) },
                actions = {
                    // 좋아요 목록 버튼 + 개수 배지
                    IconButton(onClick = onFavoritesClick) {
                        BadgedBox(
                            badge = {
                                if (state.favoriteCount > 0) Badge { Text(text = "${state.favoriteCount}") }
                            },
                        ) {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = stringResource(R.string.favorites))
                        }
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        val refresh = photos.loadState.refresh
        val contentModifier = Modifier.padding(innerPadding)
        when {
            // 첫 로드: 아직 한 장도 없을 때만 전체 로딩/에러
            refresh is LoadState.Loading && photos.itemCount == 0 -> CircularLoading(contentModifier)
            refresh is LoadState.Error && photos.itemCount == 0 ->
                ErrorView(
                    throwable = refresh.error,
                    onRetry = photos::retry,
                    modifier = contentModifier
                )

            else -> LazyVerticalStaggeredGrid(
                // 비율대로 보여주는 그리드로 변경
                columns = StaggeredGridCells.Adaptive(minSize = 160.dp),
                modifier = contentModifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                // verticalItemSpacing으로 변경 (위아래 spacing)
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(count = photos.itemCount, key = photos.itemKey { it.photo.id }) { index ->
                    val item = photos[index] ?: return@items
                    PhotoCard(
                        item = item,
                        onClick = { onPhotoClick(item.photo.id) },
                        // 누르면 반대 상태로
                        onFavoriteClick = { onFavoriteClick(item.photo, !item.isFavorite) },
                    )
                }
            }
        }
    }
}