package com.kyeongilhan.photoexplorer.data.favorite.sync

import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteImageFileDataSource
import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteLocalDataSource
import com.kyeongilhan.photoexplorer.data.photo.datasource.ImageRemoteDataSource
import com.kyeongilhan.photoexplorer.data.photo.datasource.PhotoRemoteDataSource
import javax.inject.Inject

class FavoriteImageSaver @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource,
    private val favoriteImageFileDataSource: FavoriteImageFileDataSource,
    private val photoRemoteDataSource: PhotoRemoteDataSource,
    private val imageRemoteDataSource: ImageRemoteDataSource,
) {
    // 좋아요한 사진 이미지를 기기에 저장
    suspend fun save(photoId: String) {
        // 그 사이 좋아요 해제됐으면 할 거 없음
        val favorite = favoriteLocalDataSource.getFavorite(photoId) ?: return

        // 다운로드 api는 한 번만 (재시도 때 중복 호출 방지)
        if (!favorite.isDownloadCalled) {
            photoRemoteDataSource.callDownloadApi(favorite.photo.downloadLocation)
            favoriteLocalDataSource.markDownloadCalled(photoId)
        }

        // 다운로드 하고 나서 결과를 저장한다
        val bytes = imageRemoteDataSource.downloadImage(favorite.photo.imageUrl)
        val path = favoriteImageFileDataSource.save(photoId, bytes)
        favoriteLocalDataSource.updateLocalImagePath(photoId, path)
    }

    // 좋아요 해제한 사진 이미지 삭제
    suspend fun delete(photoId: String) {
        favoriteImageFileDataSource.delete(photoId)
    }
}