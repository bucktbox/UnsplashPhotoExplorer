package com.kyeongilhan.photoexplorer.data.favorite.repository

import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteImageWorkDataSource
import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteImageRepository
import javax.inject.Inject

internal class FavoriteImageRepositoryImpl @Inject constructor(
    private val favoriteImageWorkDataSource: FavoriteImageWorkDataSource,
) : FavoriteImageRepository {
    override fun saveFavoriteImage(photoId: String) = favoriteImageWorkDataSource.enqueueSave(photoId)

    override fun removeFavoriteImage(photoId: String) = favoriteImageWorkDataSource.enqueueRemove(photoId)
}