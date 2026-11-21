package com.kyeongilhan.photoexplorer.data.photo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kyeongilhan.photoexplorer.data.photo.datasource.PhotoRemoteDataSource
import com.kyeongilhan.photoexplorer.data.photo.mapper.toDomain
import com.kyeongilhan.photoexplorer.data.photo.paging.LatestPhotoPagingSource
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import com.kyeongilhan.photoexplorer.domain.photo.model.PhotoDetail
import com.kyeongilhan.photoexplorer.domain.photo.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PhotoRepositoryImpl @Inject constructor(
    private val photoRemoteDataSource: PhotoRemoteDataSource,
) : PhotoRepository {
    // 실제 repo 구현
    // paging으로 사진 목록 가져오기 (to domain useCase)
    override fun getLatestPhotos(): Flow<PagingData<Photo>> = Pager(
        config = PagingConfig(
            pageSize = PhotoRemoteDataSource.MAX_PER_PAGE,
            initialLoadSize = PhotoRemoteDataSource.MAX_PER_PAGE,
            // 전체 개수를 알 수 있는 api는 placeholder로 미리 깔아놀 수있다
            enablePlaceholders = false,
        ),
        // 한번 쓰고 버리는 객체라 람다로 받음
        pagingSourceFactory = { LatestPhotoPagingSource(photoRemoteDataSource) },
    ).flow

    // 사진 상세 가져오기 (to domain useCase)
    override suspend fun getPhotoDetail(photoId: String): PhotoDetail =
        photoRemoteDataSource.getPhotoDetail(photoId).toDomain()
}