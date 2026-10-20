package com.kyeongilhan.photoexplorer.ui.photodetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyeongilhan.photoexplorer.domain.photo.model.Author
import com.kyeongilhan.photoexplorer.ui.common.AuthorProfileImage
import com.kyeongilhan.photoexplorer.ui.theme.PhotoExplorerTheme

@Composable
fun AuthorDetailInfo(author: Author, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // 작가: 프로필 + 이름 + @아이디
        Row(verticalAlignment = Alignment.CenterVertically) {
            AuthorProfileImage(imageUrl = author.profileImageUrl, size = 48.dp)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = author.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "@${author.username}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthorDetailInfo() {
    PhotoExplorerTheme {
        AuthorDetailInfo(
            author = Author(
                name = "Kyeongil",
                username = "Han",
                profileImageUrl = "https://images.unsplash.com/profile-1789589982089-0981af9f2b42image?ixlib=rb-4.1.0&crop=faces&fit=crop&w=64&h=64",
            ),
            modifier = Modifier,
        )
    }
}