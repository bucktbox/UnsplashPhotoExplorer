package com.kyeongilhan.photoexplorer.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyeongilhan.photoexplorer.domain.favorite.model.FavoritePhoto
import com.kyeongilhan.photoexplorer.domain.photo.model.Author
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import com.kyeongilhan.photoexplorer.presentation.photolist.PhotoCardUiModel
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.common.AuthorProfileImage
import com.kyeongilhan.photoexplorer.ui.common.FavoriteIconButton
import com.kyeongilhan.photoexplorer.ui.common.PhotoImage
import com.kyeongilhan.photoexplorer.ui.photolist.screenitem.PhotoCard
import java.io.File

@Composable
fun FavoritePhotoCard(
    favorite: FavoritePhoto,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val photo = favorite.photo

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick),
    ) {
        Box {
            PhotoImage(
                // 로컬 파일 있으면 그걸로 표시
                model = favorite.localImagePath?.let { File(it) } ?: photo.imageUrl,
                color = photo.color,
                contentDescription = photo.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f),
            )
            // 좋아요 목록이라 항상 채운 하트. 누르면 해제
            FavoriteIconButton(
                isFavorite = true,
                onClick = onRemoveClick,
                modifier = Modifier.align(Alignment.TopEnd),
            )
        }

        // 작가 영역 추가
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AuthorProfileImage(imageUrl = photo.author.profileImageUrl, size = 24.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.author_by, photo.author.name),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun FavoritePhotoCardPreview() {
    val id = "a"
    FavoritePhotoCard(
        favorite = FavoritePhoto(
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
            localImagePath = null,
            savedAt = 0L
        ),
        onClick = {},
        onRemoveClick = {},
    )
}