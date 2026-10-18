package com.kyeongilhan.photoexplorer.data.paging

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.data.photo.datasource.PhotoRemoteDataSource
import com.kyeongilhan.photoexplorer.data.photo.paging.LatestPhotoPagingSource
import com.kyeongilhan.photoexplorer.data.testdata.createPhotoEntity
import com.kyeongilhan.photoexplorer.dataresource.DomainException
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LatestPhotoPagingSourceTest {
    private val photoRemoteDataSource: PhotoRemoteDataSource = mockk()
    private lateinit var pagingSource: LatestPhotoPagingSource

    @Before
    fun setUp() {
        pagingSource = LatestPhotoPagingSource(photoRemoteDataSource)
    }

    @Test
    fun testInitialPagingLoad() = runTest {
        coEvery { photoRemoteDataSource.getLatestPhotos(1, any()) } returns
                listOf(createPhotoEntity("a"), createPhotoEntity("b"))
        val page = load(key = null) as LoadResult.Page<Int, Photo>
        assertThat(page.data).hasSize(2)
        assertThat(page.prevKey).isNull()
        assertThat(page.nextKey).isEqualTo(2)
    }

    @Test
    fun testPagingLoadError() = runTest {
        coEvery { photoRemoteDataSource.getLatestPhotos(any(), any()) } throws DomainException.Network()
        val error = load(key = null) as LoadResult.Error<Int, Photo>
        assertThat(error.throwable).isInstanceOf(DomainException.Network::class.java)
    }

    private suspend fun load(key: Int?): LoadResult<Int, Photo> =
        pagingSource.load(
            if (key == null) {
                LoadParams.Refresh(key = null, loadSize = 30, placeholdersEnabled = false)
            } else {
                LoadParams.Append(key = key, loadSize = 30, placeholdersEnabled = false)
            },
        )
}