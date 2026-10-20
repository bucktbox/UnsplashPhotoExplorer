package com.kyeongilhan.photoexplorer.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kyeongilhan.photoexplorer.ui.theme.PhotoExplorerTheme

@Composable
fun AuthorProfileImage(
    imageUrl: String?,
    size: Dp,
    modifier: Modifier = Modifier,
    ringWidth: Dp = 1.dp,
) {
    // 주소가 없거나 오프라인이면 회색 원
    val placeholder = ColorPainter(Color.LightGray)
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        placeholder = placeholder,
        error = placeholder,
        fallback = placeholder,
        contentScale = ContentScale.Crop,
        // 원형에 padding 줘서 흰 테두리 보이게 함
        modifier = modifier
            .size(size)
            .background(Color.White, CircleShape)
            .padding(ringWidth)
            .clip(CircleShape),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthorProfileImage() {
    PhotoExplorerTheme {
        AuthorProfileImage(
            imageUrl = "https://images.unsplash.com/profile-1789589982089-0981af9f2b42image?ixlib=rb-4.1.0&crop=faces&fit=crop&w=64&h=64",
            size = 32.dp,
        )
    }
}