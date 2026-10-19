package com.kyeongilhan.photoexplorer.presentation.testdata

import com.kyeongilhan.photoexplorer.domain.photo.model.Author
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo

// 테스트용 photo data
fun createPhoto(id: String): Photo = Photo(
    id = id,
    width = 300,
    height = 200,
    description = null,
    color = "#336699",
    imageUrl = "https://images.unsplash.com/photo-$id",
    author = Author(name = "Author $id", username = "author_$id", profileImageUrl = null),
    downloadLocation = "https://api.unsplash.com/photos/$id/download",
)