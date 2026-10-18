package com.kyeongilhan.photoexplorer.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kyeongilhan.photoexplorer.local.favorite.FavoritePhotoDao
import com.kyeongilhan.photoexplorer.local.favorite.FavoritePhotoRoomEntity

@Database(
    entities = [FavoritePhotoRoomEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class PhotoExplorerDatabase : RoomDatabase() {
    abstract fun favoritePhotoDao(): FavoritePhotoDao

    companion object {
        const val NAME = "photo_explorer.db"
    }
}