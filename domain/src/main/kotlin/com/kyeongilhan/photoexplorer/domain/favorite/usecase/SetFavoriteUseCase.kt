package com.kyeongilhan.photoexplorer.domain.favorite.usecase

import com.kyeongilhan.photoexplorer.domain.di.IoDispatcher
import com.kyeongilhan.photoexplorer.domain.base.BaseUseCase
import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteImageRepository
import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteRepository
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val favoriteImageRepository: FavoriteImageRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : BaseUseCase<SetFavoriteUseCase.Params, Unit>(dispatcher) {
    data class Params(
        val photo: Photo,
        val isFavorite: Boolean,
    )

    override suspend fun execute(params: Params) {
        val photoId = params.photo.id
        if (params.isFavorite) {
            // 로컬 DB 좋아요 목록에 추가
            favoriteRepository.saveFavorite(params.photo)
            // api 통해 다운로드 등록하고 이미지를 로컬에 저장하기
            favoriteImageRepository.saveFavoriteImage(photoId)
        } else {
            // 로컬 DB에서 삭제하기
            favoriteRepository.removeFavorite(photoId)
            // 저장된 이미지 삭제하기
            favoriteImageRepository.removeFavoriteImage(photoId)
        }
    }
}