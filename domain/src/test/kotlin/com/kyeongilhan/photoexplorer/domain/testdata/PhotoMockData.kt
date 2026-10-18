package com.kyeongilhan.photoexplorer.domain.testdata

import com.kyeongilhan.photoexplorer.domain.photo.model.Author
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo

// 테스트용 photo data (mockK 사용)
fun createPhoto(id: String = "a", width: Int = 300, height: Int = 200): Photo = Photo(
    id = id,
    width = width,
    height = height,
    description = "photo $id",
    color = "#336699",
    imageUrl = "https://images.unsplash.com/photo-$id?ixid=abc$id&ixlib=rb-4.0.3",
    author = Author(name = "Author $id", username = "author_$id", profileImageUrl = null),
    downloadLocation = "https://api.unsplash.com/photos/$id/download?ixid=abc$id",
)
