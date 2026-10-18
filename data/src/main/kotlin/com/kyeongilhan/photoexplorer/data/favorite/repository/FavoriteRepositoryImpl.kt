package com.kyeongilhan.photoexplorer.data.favorite.repository

import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteLocalDataSource
import com.kyeongilhan.photoexplorer.data.favorite.mapper.toDomain
import com.kyeongilhan.photoexplorer.data.favorite.mapper.toFavoriteEntity
import com.kyeongilhan.photoexplorer.domain.favorite.model.FavoritePhoto
import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteRepository
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource,
) : FavoriteRepository {
    override fun observeFavoriteIds(): Flow<Set<String>> =
        favoriteLocalDataSource.observeFavoriteIds()
            .map { it.toSet() }
            // 값 바뀌면 가져오기 (안바뀌면 다시 조회 안함)
            .distinctUntilChanged()

    override fun observeFavorites(): Flow<List<FavoritePhoto>> =
        favoriteLocalDataSource.observeFavorites()
            .map { entities -> entities.map { it.toDomain() } }
            .distinctUntilChanged()

    override fun observeFavorite(photoId: String): Flow<FavoritePhoto?> =
        favoriteLocalDataSource.observeFavorite(photoId)
            .map { it?.toDomain() }
            .distinctUntilChanged()

    override suspend fun saveFavorite(photo: Photo) {
        favoriteLocalDataSource.insertFavorite(photo.toFavoriteEntity(savedAt = System.currentTimeMillis()))
    }

    override suspend fun removeFavorite(photoId: String) {
        favoriteLocalDataSource.deleteFavorite(photoId)
    }
}