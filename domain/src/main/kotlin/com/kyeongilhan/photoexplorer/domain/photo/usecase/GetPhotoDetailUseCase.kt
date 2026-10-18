package com.kyeongilhan.photoexplorer.domain.photo.usecase

import com.kyeongilhan.photoexplorer.domain.di.IoDispatcher
import com.kyeongilhan.photoexplorer.dataresource.DataResource
import com.kyeongilhan.photoexplorer.domain.base.BaseFlowUseCase
import com.kyeongilhan.photoexplorer.domain.favorite.model.FavoritePhoto
import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteRepository
import com.kyeongilhan.photoexplorer.domain.photo.model.PhotoDetail
import com.kyeongilhan.photoexplorer.domain.photo.model.PhotoDetailResult
import com.kyeongilhan.photoexplorer.domain.photo.repository.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GetPhotoDetailUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val favoriteRepository: FavoriteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
    // BaseFlowUseCase 사용 (Favorite 와 합쳐야 하기 때문)
) : BaseFlowUseCase<GetPhotoDetailUseCase.Params, DataResource<PhotoDetailResult>>(dispatcher) {


    data class Params(val photoId: String)

    override fun execute(params: Params): Flow<DataResource<PhotoDetailResult>> = flow {
        emit(DataResource.Loading())

        val detail = try {
            photoRepository.getPhotoDetail(params.photoId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(DataResource.Error(e))
            return@flow
        }

        // photoDetail api 결과에 favorite (local에 저장된 좋아요 데이터) 와 결합하여 돌려준다
        emitAll(
            favoriteRepository.observeFavorite(params.photoId).map { favorite ->
                toResult(detail, favorite)
            },
        )
    }.distinctUntilChanged()

    private fun toResult(detail: PhotoDetail, favorite: FavoritePhoto?): DataResource<PhotoDetailResult> =
        DataResource.Success(
            PhotoDetailResult(
                photo = detail.photo,
                stats = detail.stats,
                isFavorite = favorite != null,
                localImagePath = favorite?.localImagePath,
            ),
        )
}