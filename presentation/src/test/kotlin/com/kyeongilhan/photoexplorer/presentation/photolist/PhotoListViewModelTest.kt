package com.kyeongilhan.photoexplorer.presentation.photolist

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.dataresource.DataResource
import com.kyeongilhan.photoexplorer.domain.favorite.usecase.ObserveFavoriteIdsUseCase
import com.kyeongilhan.photoexplorer.domain.favorite.usecase.SetFavoriteUseCase
import com.kyeongilhan.photoexplorer.domain.photo.usecase.GetLatestPhotoPagingUseCase
import com.kyeongilhan.photoexplorer.presentation.rule.MainDispatcherRule
import com.kyeongilhan.photoexplorer.presentation.testdata.createPhoto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotoListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getLatestPhotoPaging: GetLatestPhotoPagingUseCase = mockk()
    private val observeFavoriteIds: ObserveFavoriteIdsUseCase = mockk()
    private val setFavorite: SetFavoriteUseCase = mockk()
    private lateinit var viewModel: PhotoListViewModel

    private val photoA = createPhoto("a")
    private val photoB = createPhoto("b")

    @Before
    fun setUp() {
        // 더 받을 페이지가 없는 상태로 사진 두 장
        val loaded = LoadState.NotLoading(endOfPaginationReached = true)
        every { getLatestPhotoPaging() } returns flowOf(
            PagingData.from(listOf(photoA, photoB), LoadStates(loaded, loaded, loaded)),
        )
        // a만 좋아요
        every { observeFavoriteIds() } returns flowOf(setOf("a"))
        viewModel = PhotoListViewModel(getLatestPhotoPaging, observeFavoriteIds, setFavorite)
    }

    @Test
    fun testPhotoListWithFavoriteIds() = runTest {
        val items = viewModel.photos.asSnapshot()

        // 현재 photo list에서 a만 좋아요인지 확인
        assertThat(items.map { it.photo.id }).containsExactly("a", "b").inOrder()
        assertThat(items.map { it.isFavorite }).containsExactly(true, false).inOrder()
    }

    @Test
    fun testClickFavoriteCallsSetFavorite() = runTest {
        every { setFavorite(any()) } returns flowOf(DataResource.Success(Unit))

        viewModel.onIntent(PhotoListIntent.ClickFavorite(photo = photoB, isFavorite = true))

        // usecase가 그 값으로 불렸는지 확인
        verify { setFavorite(SetFavoriteUseCase.Params(photo = photoB, isFavorite = true)) }
    }
}