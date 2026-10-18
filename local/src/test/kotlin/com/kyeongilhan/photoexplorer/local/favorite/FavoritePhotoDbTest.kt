package com.kyeongilhan.photoexplorer.local.favorite

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.local.db.PhotoExplorerDatabase
import com.kyeongilhan.photoexplorer.local.favorite.room.FavoritePhotoDao
import com.kyeongilhan.photoexplorer.local.favorite.room.FavoritePhotoRoomEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class FavoritePhotoDbTest {
    private lateinit var database: PhotoExplorerDatabase
    private lateinit var dao: FavoritePhotoDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, PhotoExplorerDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.favoritePhotoDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testObserveFavoritesWhenInsertAndDelete() = runTest {
        dao.observeFavorites().test {
            assertThat(awaitItem()).isEmpty()

            dao.insertFavorite(createRoomEntity("a"))
            assertThat(awaitItem().map { it.id }).containsExactly("a")

            dao.deleteFavorite("a")
            assertThat(awaitItem()).isEmpty()
        }
    }

    @Test
    fun testInsertFavorite() = runTest {
        val rowId = dao.insertFavorite(createRoomEntity("a"))
        dao.markDownloadCalled("a")
        dao.updateLocalImagePath("a", "/files/favorites/a.webp")

        val row = dao.getFavorite("a")!!

        assertThat(row.downloadCalled).isTrue()
        assertThat(row.localImagePath).isEqualTo("/files/favorites/a.webp")
    }


    private fun createRoomEntity(id: String, savedAt: Long = 0) = FavoritePhotoRoomEntity(
        id = id,
        width = 300,
        height = 200,
        description = null,
        color = "#000000",
        imageUrl = "https://images.unsplash.com/photo-$id?w=1080",
        authorName = "Han",
        authorUsername = "kyeongil",
        authorProfileImageUrl = null,
        downloadLocation = "https://api.unsplash.com/photos/$id/download",
        savedAt = savedAt,
    )
}