package com.kyeongilhan.photoexplorer.local.di

import android.content.Context
import androidx.room.Room
import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteLocalDataSource
import com.kyeongilhan.photoexplorer.local.db.PhotoExplorerDatabase
import com.kyeongilhan.photoexplorer.local.favorite.FavoriteLocalDataSourceImpl
import com.kyeongilhan.photoexplorer.local.favorite.FavoritePhotoDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface LocalModule {
    @Binds
    fun bindFavoriteLocalDataSource(impl: FavoriteLocalDataSourceImpl): FavoriteLocalDataSource

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): PhotoExplorerDatabase =
            Room.databaseBuilder(context, PhotoExplorerDatabase::class.java, PhotoExplorerDatabase.NAME).build()

        @Provides
        fun provideFavoritePhotoDao(database: PhotoExplorerDatabase): FavoritePhotoDao = database.favoritePhotoDao()
    }
}