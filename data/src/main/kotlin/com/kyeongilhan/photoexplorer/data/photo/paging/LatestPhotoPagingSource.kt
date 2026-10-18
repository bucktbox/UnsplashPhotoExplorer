package com.kyeongilhan.photoexplorer.data.photo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kyeongilhan.photoexplorer.data.photo.datasource.PhotoRemoteDataSource
import com.kyeongilhan.photoexplorer.data.photo.mapper.toDomain
import com.kyeongilhan.photoexplorer.dataresource.toDomainException
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import kotlinx.coroutines.CancellationException

internal class LatestPhotoPagingSource(
    private val photoRemoteDataSource: PhotoRemoteDataSource,
    private val perPage: Int = PhotoRemoteDataSource.MAX_PER_PAGE,
) : PagingSource<Int, Photo>() {

    // Android Paging Load 구현
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: FIRST_PAGE
        return try {
            val entities = photoRemoteDataSource.getLatestPhotos(page = page, perPage = perPage)
            // 가져온 entity를 domain으로 변환하여 반환 (domain 레이어로 올리기 위함)
            LoadResult.Page(
                data = entities.map { it.toDomain() },
                // 계속 이어서 조회니까 이전 페이지는 조회 안함 (계속 append)
                prevKey = null,
                // 이전 페이지 마지막 번호 + 1 이 다음 조회 key
                nextKey = if (entities.isEmpty()) null else page + 1,
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            LoadResult.Error(e.toDomainException())
        }
    }

    // refresh key 구현
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? = null

    companion object {
        const val FIRST_PAGE = 1
    }
}
