package com.kyeongilhan.photoexplorer.domain.favorite.usecase

import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteImageRepository
import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteRepository
import com.kyeongilhan.photoexplorer.domain.testdata.createPhoto
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetFavoriteUseCaseTest {
    private val favoriteRepository: FavoriteRepository = mockk(relaxUnitFun = true)
    private val favoriteImageRepository: FavoriteImageRepository =
        mockk(relaxUnitFun = true)
    private lateinit var useCase: SetFavoriteUseCase
    private val photo = createPhoto("a")

    @Before
    fun setUp() {
        useCase = SetFavoriteUseCase(
            favoriteRepository = favoriteRepository,
            favoriteImageRepository = favoriteImageRepository,
            dispatcher = Dispatchers.Unconfined,
        )
    }

    @Test
    fun testFavoriteTrueUseCase() = runTest {
        // 사진 좋아요 누르면 동작 하는 순서 테스트
        useCase.run(SetFavoriteUseCase.Params(photo, isFavorite = true))
        coVerifyOrder {
            favoriteRepository.saveFavorite(photo)
            favoriteImageRepository.saveFavoriteImage("a")
        }
    }

    @Test
    fun testFavoriteFalseUseCase() = runTest {
        // 사진 좋아요 취소하면 동작 하는 순서 테스트
        useCase.run(SetFavoriteUseCase.Params(photo, isFavorite = false))
        coVerifyOrder {
            favoriteRepository.removeFavorite("a")
            favoriteImageRepository.removeFavoriteImage("a")
        }
    }
}