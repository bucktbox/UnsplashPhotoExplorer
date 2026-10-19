package com.kyeongilhan.photoexplorer.ui.common

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun PhotoImage(
    model: Any?,
    color: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        contentScale = contentScale,
        // 이미지가 오기 전에는 사진 대표 색으로 칠해 둠 (blur_hash 대신)
        modifier = modifier.background(color.toComposeColor()),
    )
}

// "#336699" → Color (없으면 회색)
private fun String?.toComposeColor(): Color =
    this?.let { runCatching { Color(android.graphics.Color.parseColor(it)) }.getOrNull() } ?: Color.LightGray