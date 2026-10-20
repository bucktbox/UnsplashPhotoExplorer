package com.kyeongilhan.photoexplorer.domain.photo.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.dataresource.DataResource
import com.kyeongilhan.photoexplorer.dataresource.DomainException
import com.kyeongilhan.photoexplorer.domain.favorite.model.FavoritePhoto
import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteRepository
import com.kyeongilhan.photoexplorer.domain.photo.repository.PhotoRepository
import com.kyeongilhan.photoexplorer.domain.testdata.createPhoto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPhotoDetailUseCaseTest {
    private val photoRepository: PhotoRepository = mockk()
    private val favoriteRepository: FavoriteRepository = mockk()
    private lateinit var useCase: GetPhotoDetailUseCase
    private val params = GetPhotoDetailUseCase.Params(photoId = "a")

    // mock data
    private val photo = createPhoto("a")
    private val favorite = MutableStateFlow<FavoritePhoto?>(null)

    @Before
    fun setUp() {
        every { favoriteRepository.observeFavorite("a") } returns favorite
        useCase = GetPhotoDetailUseCase(
            photoRepository = photoRepository,
            favoriteRepository = favoriteRepository,
            dispatcher = Dispatchers.Unconfined,
        )
    }

    @Test
    fun testGetPhotoDetailThrowsNetworkException() = runTest {
        coEvery { photoRepository.getPhotoDetail("a") } throws
                DomainException.Network()
        useCase(params).test {
            // 로딩 건너뛰기 (첫번째는 로딩)
            skipItems(1)
            val error = awaitItem() as DataResource.Error
            // 두번째로 온 아이템이 network exception 이냐 (flow 잘 동작하는지 확인)
            assertThat(error.throwable).isInstanceOf(DomainException.Network::class.java)
            awaitComplete()
        }
    }

    @Test
    fun testShowSavedPhotoWhenServerFails() = runTest {
        // 네트워크 이슈로 조회 실패 시 저장된 local image path 를 정상적으로 가져오나
        coEvery { photoRepository.getPhotoDetail("a") } throws DomainException.Network()
        favorite.value = FavoritePhoto(photo, localImagePath = "/files/favorites/a.webp", savedAt = 0L)

        useCase(params).test {
            skipItems(1)
            val result = (awaitItem() as DataResource.Success).data
            // 서버가 실패해도 저장본으로 성공
            assertThat(result.stats).isNull()
            assertThat(result.localImagePath).isEqualTo("/files/favorites/a.webp")
        }
    }
}