package com.kyeongilhan.photoexplorer.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.theme.PhotoExplorerTheme

@Composable
fun FavoriteIconButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(if (isFavorite) R.string.favorite_remove else R.string.favorite_add),
            // 사진 위에 올라가서 빈 하트는 흰색
            tint = if (isFavorite) Color.Red else Color.White,
        )
    }
}

@Preview
@Composable
fun PreviewFavoriteButton() {
    PhotoExplorerTheme {
        FavoriteIconButton(isFavorite = true, onClick = {})
    }
}