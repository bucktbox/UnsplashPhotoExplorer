package com.kyeongilhan.photoexplorer.presentation.photodetail

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.dataresource.DataResource
import com.kyeongilhan.photoexplorer.domain.favorite.usecase.SetFavoriteUseCase
import com.kyeongilhan.photoexplorer.domain.photo.model.PhotoDetailResult
import com.kyeongilhan.photoexplorer.domain.photo.usecase.GetPhotoDetailUseCase
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

class PhotoDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getPhotoDetail: GetPhotoDetailUseCase = mockk()
    private val setFavorite: SetFavoriteUseCase = mockk()

    private val photo = createPhoto("a")
    private val detail = PhotoDetailResult(
        photo = photo,
        stats = null,
        isFavorite = false,
        localImagePath = null,
    )

    @Before
    fun setUp() {
        every { getPhotoDetail(GetPhotoDetailUseCase.Params("a")) } returns
                flowOf(DataResource.Loading(), DataResource.Success(detail))
    }

    @Test
    fun testShowPhotoDetail() = runTest {
        val viewModel = createViewModel()

        val state = viewModel.state.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.detail).isEqualTo(detail)
    }

    @Test
    fun testClickFavoriteCallsSetFavorite() = runTest {
        every { setFavorite(any()) } returns flowOf(DataResource.Success(Unit))
        val viewModel = createViewModel()

        viewModel.onIntent(PhotoDetailIntent.ClickFavorite)

        // 좋아요 안 된 사진이라 좋아요로
        verify { setFavorite(SetFavoriteUseCase.Params(photo = photo, isFavorite = true)) }
    }

    // 화면 이동 때 Navigation이 넣어 주는 값을 직접 넣음
    private fun createViewModel() = PhotoDetailViewModel(
        savedStateHandle = SavedStateHandle(mapOf("photoId" to "a")),
        getPhotoDetail = getPhotoDetail,
        setFavorite = setFavorite,
    )
}