package com.kyeongilhan.photoexplorer.ui.photolist.screenitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyeongilhan.photoexplorer.domain.photo.model.Author
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import com.kyeongilhan.photoexplorer.presentation.photolist.PhotoCardUiModel
import com.kyeongilhan.photoexplorer.ui.common.FavoriteIconButton
import com.kyeongilhan.photoexplorer.ui.common.PhotoImage

@Composable
fun PhotoCard(
    item: PhotoCardUiModel,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val photo = item.photo
    // 이미지의 비율 계산
    //val ratio = if (photo.height > 0) photo.width.toFloat() / photo.height else 1f

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
    ) {
        PhotoImage(
            model = photo.imageUrl,
            color = photo.color,
            contentDescription = photo.description,
            // 일단 정사각형 칸 (넘치는 부분은 잘림)
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        )
        FavoriteIconButton(
            isFavorite = item.isFavorite,
            onClick = onFavoriteClick,
            modifier = Modifier.align(Alignment.TopEnd),
        )
    }
}

@Preview
@Composable
fun PhotoCardPreview() {
    val id = "a"
    PhotoCard(
        item = PhotoCardUiModel(
            photo = Photo(
                id = id,
                width = 300,
                height = 200,
                description = null,
                color = "#336699",
                imageUrl = "https://images.unsplash.com/photo-$id",
                author = Author(
                    name = "Author $id",
                    username = "author_$id",
                    profileImageUrl = null
                ),
                downloadLocation = "https://api.unsplash.com/photos/$id/download",
            ),
            isFavorite = false,
        ),
        onClick = {},
        onFavoriteClick = {},
    )
}