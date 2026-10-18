package com.kyeongilhan.photoexplorer.local.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePhotoDao {
    @Query("SELECT * FROM favorite_photo ORDER BY savedAt DESC")
    fun observeFavorites(): Flow<List<FavoritePhotoRoomEntity>>

    @Query("SELECT id FROM favorite_photo")
    fun observeFavoriteIds(): Flow<List<String>>

    @Query("SELECT * FROM favorite_photo WHERE id = :id")
    fun observeFavorite(id: String): Flow<FavoritePhotoRoomEntity?>

    @Query("SELECT * FROM favorite_photo WHERE id = :id")
    suspend fun getFavorite(id: String): FavoritePhotoRoomEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(entity: FavoritePhotoRoomEntity): Long

    @Query("DELETE FROM favorite_photo WHERE id = :id")
    suspend fun deleteFavorite(id: String): Int

    @Query("UPDATE favorite_photo SET downloadCalled = 1 WHERE id = :id")
    suspend fun markDownloadCalled(id: String): Int

    @Query("UPDATE favorite_photo SET localImagePath = :path WHERE id = :id")
    suspend fun updateLocalImagePath(id: String, path: String): Int
}