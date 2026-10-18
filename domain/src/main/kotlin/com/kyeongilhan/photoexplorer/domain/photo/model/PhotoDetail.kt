package com.kyeongilhan.photoexplorer.domain.photo.model

data class PhotoDetail(
    val photo: Photo,
    val stats: PhotoStats?,
)

data class PhotoStats(
    // 조회수
    val views: Long?,
    // 다운로드수
    val downloads: Long?,
    // 좋아요 수
    val likes: Int?,
    // 사진 메타데이터
    val exif: Exif?,
    val location: String?,
    val tags: List<String>,
)

data class Exif(
    //val make: String?,
    //val model: String?,
    // 사진기 이름만 쓰자
    val name: String?,
    //val exposureTime: String?,
    //val aperture: String?,
    //val focalLength: String?,
    //val iso: Int?,
)
