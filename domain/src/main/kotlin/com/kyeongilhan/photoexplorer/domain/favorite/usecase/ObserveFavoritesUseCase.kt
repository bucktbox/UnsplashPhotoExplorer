package com.kyeongilhan.photoexplorer.domain.favorite.usecase

import com.kyeongilhan.photoexplorer.domain.di.IoDispatcher
import com.kyeongilhan.photoexplorer.domain.base.BaseNoParamsFlowUseCase
import com.kyeongilhan.photoexplorer.domain.favorite.model.FavoritePhoto
import com.kyeongilhan.photoexplorer.domain.favorite.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : BaseNoParamsFlowUseCase<List<FavoritePhoto>>(dispatcher) {
    override fun execute(): Flow<List<FavoritePhoto>> = favoriteRepository.observeFavorites()
}
