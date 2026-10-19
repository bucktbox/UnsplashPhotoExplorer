package com.kyeongilhan.photoexplorer.local.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.kyeongilhan.photoexplorer.domain.di.IoDispatcher
import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteImageFileDataSource
import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteImageWorkDataSource
import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteLocalDataSource
import com.kyeongilhan.photoexplorer.local.db.PhotoExplorerDatabase
import com.kyeongilhan.photoexplorer.local.favorite.FavoriteImageFileDataSourceImpl
import com.kyeongilhan.photoexplorer.local.favorite.FavoriteImageWorkDataSourceImpl
import com.kyeongilhan.photoexplorer.local.favorite.FavoriteLocalDataSourceImpl
import com.kyeongilhan.photoexplorer.local.favorite.room.FavoritePhotoDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface LocalModule {
    @Binds
    fun bindFavoriteLocalDataSource(impl: FavoriteLocalDataSourceImpl): FavoriteLocalDataSource

    @Binds
    fun bindFavoriteImageWorkDataSource(impl: FavoriteImageWorkDataSourceImpl): FavoriteImageWorkDataSource

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): PhotoExplorerDatabase =
            Room.databaseBuilder(context, PhotoExplorerDatabase::class.java, PhotoExplorerDatabase.NAME).build()

        @Provides
        fun provideFavoritePhotoDao(database: PhotoExplorerDatabase): FavoritePhotoDao = database.favoritePhotoDao()

        @Provides
        @Singleton
        fun provideFavoriteImageFileDataSource(
            @ApplicationContext context: Context,
            @IoDispatcher ioDispatcher: CoroutineDispatcher,
        ): FavoriteImageFileDataSource =
            FavoriteImageFileDataSourceImpl(context.filesDir, ioDispatcher)

        // companion object 안
        @Provides
        @Singleton
        fun provideWorkManager(@ApplicationContext context: Context): WorkManager = WorkManager.getInstance(context)
    }
}