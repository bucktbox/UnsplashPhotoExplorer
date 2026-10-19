package com.kyeongilhan.photoexplorer.ui.photolist

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.kyeongilhan.photoexplorer.presentation.photolist.PhotoListIntent
import com.kyeongilhan.photoexplorer.presentation.photolist.PhotoListViewModel

@Composable
fun PhotoListWrapper(
    onPhotoClick: (String) -> Unit,
    viewModel: PhotoListViewModel = hiltViewModel(),
) {
    val photos = viewModel.photos.collectAsLazyPagingItems()

    // 화면에 photo list paging data 넘기기
    PhotoListScreen(
        photos = photos,
        onPhotoClick = onPhotoClick,
        onFavoriteClick = { photo, isFavorite ->
            viewModel.onIntent(PhotoListIntent.ClickFavorite(photo = photo, isFavorite = isFavorite))
        },
    )
}