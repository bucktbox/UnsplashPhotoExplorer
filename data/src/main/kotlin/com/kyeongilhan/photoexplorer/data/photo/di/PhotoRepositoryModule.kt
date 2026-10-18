package com.kyeongilhan.photoexplorer.data.photo.di

import com.kyeongilhan.photoexplorer.data.photo.repository.PhotoRepositoryImpl
import com.kyeongilhan.photoexplorer.domain.photo.repository.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface PhotoRepositoryModule {
    @Binds
    fun bindPhotoRepository(impl: PhotoRepositoryImpl): PhotoRepository
}