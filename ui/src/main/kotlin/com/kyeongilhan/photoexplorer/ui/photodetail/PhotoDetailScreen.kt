package com.kyeongilhan.photoexplorer.ui.photodetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kyeongilhan.photoexplorer.domain.photo.model.PhotoDetailResult
import com.kyeongilhan.photoexplorer.presentation.photodetail.PhotoDetailState
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.common.CircularLoading
import com.kyeongilhan.photoexplorer.ui.common.ErrorView
import com.kyeongilhan.photoexplorer.ui.common.FavoriteIconButton
import com.kyeongilhan.photoexplorer.ui.common.PhotoImage
import com.kyeongilhan.photoexplorer.ui.photodetail.screenitem.AuthorDetailInfo
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailScreen(
    state: PhotoDetailState,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onRetry: () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    val detail = state.detail
    val error = state.error

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.photo_detail)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    // 좋아요 버튼
                    // 상세를 받은 뒤에만 하트 표시 (로딩, 에러 중엔 숨김)
                    if (detail != null) {
                        FavoriteIconButton(
                            isFavorite = detail.isFavorite,
                            onClick = onFavoriteClick
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)

        when {
            detail != null -> PhotoDetailContent(detail = detail, modifier = contentModifier)
            error != null -> ErrorView(throwable = error, onRetry = onRetry, modifier = contentModifier)
            else -> CircularLoading(contentModifier)
        }
    }
}

@Composable
private fun PhotoDetailContent(
    detail: PhotoDetailResult,
    modifier: Modifier = Modifier,
) {
    val photo = detail.photo
    val ratio = if (photo.height > 0) photo.width.toFloat() / photo.height else 1f
    // 저장된거 있으면 그걸로 띄우고 없으면 imageUrl로 띄운다
    val imageModel = remember(photo.id) {
        detail.localImagePath?.let { File(it) } ?: photo.imageUrl
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Box {
            PhotoImage(
                // 저장된 파일이 있으면 파일, 없으면 서버 이미지
                model = imageModel,
                color = photo.color,
                contentDescription = photo.description,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(ratio),
            )
            /*FavoriteIconButton(
                isFavorite = detail.isFavorite,
                onClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.TopEnd),
            )*/
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AuthorDetailInfo(author = photo.author, modifier = Modifier)
            Spacer(modifier = Modifier.width(8.dp))
            photo.description?.let { Text(text = it, style = MaterialTheme.typography.bodyLarge) }
        }
    }
}