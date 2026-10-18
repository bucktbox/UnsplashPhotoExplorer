package com.kyeongilhan.photoexplorer.data.sync

import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteImageFileDataSource
import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteLocalDataSource
import com.kyeongilhan.photoexplorer.data.favorite.sync.FavoriteImageSaver
import com.kyeongilhan.photoexplorer.data.photo.datasource.ImageRemoteDataSource
import com.kyeongilhan.photoexplorer.data.photo.datasource.PhotoRemoteDataSource
import com.kyeongilhan.photoexplorer.data.testdata.createFavoriteEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FavoriteImageSaverTest {
    // relaxed = true: 반환값 없는 함수는 따로 안 정해도 됨
    private val favoriteLocalDataSource: FavoriteLocalDataSource = mockk(relaxed = true)
    private val favoriteImageFileDataSource: FavoriteImageFileDataSource = mockk(relaxed = true)
    private val photoRemoteDataSource: PhotoRemoteDataSource = mockk(relaxed = true)
    private val imageRemoteDataSource: ImageRemoteDataSource = mockk(relaxed = true)
    private lateinit var saver: FavoriteImageSaver

    @Before
    fun setUp() {
        coEvery { imageRemoteDataSource.downloadImage(any()) } returns byteArrayOf(1)
        coEvery { favoriteImageFileDataSource.save(any(), any()) } returns "/files/a.webp"
        saver = FavoriteImageSaver(
            favoriteLocalDataSource,
            favoriteImageFileDataSource,
            photoRemoteDataSource,
            imageRemoteDataSource,
        )
    }

    @Test
    fun testSaveImageSuccess() = runTest {
        val favorite = createFavoriteEntity("a")
        coEvery { favoriteLocalDataSource.getFavorite("a") } returns favorite
        coEvery { favoriteImageFileDataSource.save("a", any()) } returns "/files/a.webp"

        saver.save("a")

        // 다운로드 api 부르고, 저장한 파일 경로를 기록
        coVerify { photoRemoteDataSource.callDownloadApi(favorite.photo.downloadLocation) }
        coVerify { favoriteLocalDataSource.updateLocalImagePath("a", "/files/a.webp") }
    }

    @Test
    fun testSkipDownloadApiWhenAlreadyCalled() = runTest {
        val favorite = createFavoriteEntity("a").copy(isDownloadCalled = true)
        coEvery { favoriteLocalDataSource.getFavorite("a") } returns favorite

        saver.save("a")

        // 이미 불렀으면 다시 안 부름
        coVerify(exactly = 0) { photoRemoteDataSource.callDownloadApi(any()) }
    }

    @Test
    fun testSaveNothingWhenUnfavorited() = runTest {
        coEvery { favoriteLocalDataSource.getFavorite("a") } returns null

        saver.save("a")

        // 좋아요 해제됐으면 이미지 안 받음
        coVerify(exactly = 0) { imageRemoteDataSource.downloadImage(any()) }
    }
}