package com.kyeongilhan.photoexplorer.data.favorite.datasource

interface FavoriteImageWorkDataSource {
    // 이미지 저장 작업 예약
    fun enqueueSave(photoId: String)
    // 이미지 삭제 작업 예약
    fun enqueueRemove(photoId: String)
}