package com.kyeongilhan.photoexplorer.domain.photo.model

// 화면에 필요한 사진 상세보기 데이터
data class PhotoDetailResult(
    val photo: Photo,
    val stats: PhotoStats?,
    // 로컬 좋아요 한것인가
    val isFavorite: Boolean,
    // 좋아요 한 것 중 다운로드 로컬 DB에 저장된 위치 (오프라인에서 확인 가능해야함)
    val localImagePath: String?,
)