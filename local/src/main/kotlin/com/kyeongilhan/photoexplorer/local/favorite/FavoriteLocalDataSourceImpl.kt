package com.kyeongilhan.photoexplorer.local.favorite

import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteLocalDataSource
import com.kyeongilhan.photoexplorer.data.favorite.entity.FavoritePhotoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FavoriteLocalDataSourceImpl @Inject constructor(
    private val favoritePhotoDao: FavoritePhotoDao,
) : FavoriteLocalDataSource {
    override fun observeFavorites(): Flow<List<FavoritePhotoEntity>> =
        favoritePhotoDao.observeFavorites().map { rows -> rows.map { it.toEntity() } }

    override fun observeFavoriteIds(): Flow<List<String>> = favoritePhotoDao.observeFavoriteIds()

    override fun observeFavorite(photoId: String): Flow<FavoritePhotoEntity?> =
        favoritePhotoDao.observeFavorite(photoId).map { it?.toEntity() }

    override suspend fun getFavorite(photoId: String): FavoritePhotoEntity? =
        favoritePhotoDao.getFavorite(photoId)?.toEntity()

    override suspend fun insertFavorite(entity: FavoritePhotoEntity) {
        // 이미 있으면 무시 (Dao의 IGNORE). 저장 시각, 트래킹, 파일 경로를 덮어쓰지 않음
        favoritePhotoDao.insertFavorite(entity.toRoomEntity())
    }

    override suspend fun deleteFavorite(photoId: String) {
        favoritePhotoDao.deleteFavorite(photoId)
    }

    override suspend fun markDownloadCalled(photoId: String) {
        favoritePhotoDao.markDownloadCalled(photoId)
    }

    override suspend fun updateLocalImagePath(photoId: String, path: String) {
        favoritePhotoDao.updateLocalImagePath(photoId, path)
    }
}