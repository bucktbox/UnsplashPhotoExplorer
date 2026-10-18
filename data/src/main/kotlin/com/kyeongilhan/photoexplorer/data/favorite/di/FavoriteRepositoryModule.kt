package com.kyeongilhan.photoexplorer.data.favorite.di

import com.kyeongilhan.photoexplorer.data.favorite.repository.FavoriteRepositoryImpl
import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface FavoriteRepositoryModule {
    @Binds
    fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository
}