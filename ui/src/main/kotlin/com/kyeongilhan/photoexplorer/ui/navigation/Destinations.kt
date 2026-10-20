package com.kyeongilhan.photoexplorer.ui.navigation

import kotlinx.serialization.Serializable

// 화면 주소. NavHost(app)가 이 타입으로 화면을 찾음
@Serializable
data object PhotoListDestination

// 상세는 사진 id 하나만 넘김
@Serializable
data class PhotoDetailDestination(val photoId: String)