package com.kyeongilhan.photoexplorer.domain.photo.usecase

import androidx.paging.PagingData
import com.kyeongilhan.photoexplorer.component.di.IoDispatcher
import com.kyeongilhan.photoexplorer.domain.base.BaseNoParamsFlowUseCase
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import com.kyeongilhan.photoexplorer.domain.photo.repository.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestPhotoPagingUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
    // BaseNoParamsFlowUseCase 사용 -> 페이지 계속 붙는다
) : BaseNoParamsFlowUseCase<PagingData<Photo>>(dispatcher) {
    override fun execute(): Flow<PagingData<Photo>> = photoRepository.getLatestPhotos()
}