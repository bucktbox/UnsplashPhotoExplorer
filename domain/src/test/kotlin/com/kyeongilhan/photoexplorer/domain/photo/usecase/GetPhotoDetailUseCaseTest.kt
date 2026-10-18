package com.kyeongilhan.photoexplorer.domain.photo.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.dataresource.DataResource
import com.kyeongilhan.photoexplorer.dataresource.DomainException
import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteRepository
import com.kyeongilhan.photoexplorer.domain.photo.repository.PhotoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPhotoDetailUseCaseTest {
    private val photoRepository: PhotoRepository = mockk()
    private val favoriteRepository: FavoriteRepository = mockk()
    private lateinit var useCase: GetPhotoDetailUseCase
    private val params = GetPhotoDetailUseCase.Params(photoId = "a")
    @Before
    fun setUp() {
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
}