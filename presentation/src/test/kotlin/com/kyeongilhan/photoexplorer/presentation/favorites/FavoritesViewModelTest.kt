package com.kyeongilhan.photoexplorer.presentation.favorites

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.dataresource.DataResource
import com.kyeongilhan.photoexplorer.domain.favorite.usecase.ObserveFavoritesUseCase
import com.kyeongilhan.photoexplorer.domain.favorite.usecase.SetFavoriteUseCase
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

class FavoritesViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val observeFavorites: ObserveFavoritesUseCase = mockk()
    private val setFavorite: SetFavoriteUseCase = mockk()
    private lateinit var viewModel: FavoritesViewModel

    private val photo = createPhoto("a")

    @Before
    fun setUp() {
        every { observeFavorites() } returns flowOf(emptyList())
        every { setFavorite(any()) } returns flowOf(DataResource.Success(Unit))
        viewModel = FavoritesViewModel(observeFavorites, setFavorite)
    }

    @Test
    fun testRemoveSendsShowUndo() = runTest {
        viewModel.effect.test {
            viewModel.onIntent(FavoritesIntent.Remove(photo))

            // 해제하고 스낵바를 띄우라고 알림
            assertThat(awaitItem()).isEqualTo(FavoritesEffect.ShowSuccess)
        }
        // usecase 호출된거에 들어간 param이 올바른 것인지 확인
        verify { setFavorite(SetFavoriteUseCase.Params(photo = photo, isFavorite = false)) }
    }
}