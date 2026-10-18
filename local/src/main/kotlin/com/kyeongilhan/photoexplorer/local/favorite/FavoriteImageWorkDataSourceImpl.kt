package com.kyeongilhan.photoexplorer.local.favorite

import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteImageWorkDataSource
import com.kyeongilhan.photoexplorer.local.favorite.sync.FavoriteImageWorker
import com.kyeongilhan.photoexplorer.local.favorite.sync.FavoriteImageWorker.Companion.KEY_IS_SAVE
import com.kyeongilhan.photoexplorer.local.favorite.sync.FavoriteImageWorker.Companion.KEY_PHOTO_ID
import javax.inject.Inject

internal class FavoriteImageWorkDataSourceImpl @Inject constructor(
    private val workManager: WorkManager,
) : FavoriteImageWorkDataSource {

    override fun enqueueSave(photoId: String) {
        val request = OneTimeWorkRequestBuilder<FavoriteImageWorker>()
            .setInputData(workDataOf(KEY_PHOTO_ID to photoId, KEY_IS_SAVE to true))
            // 네트워크 있을 때만 실행
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()
        // 사진별로 작업 하나만
        workManager.enqueueUniqueWork("favorite-$photoId", ExistingWorkPolicy.REPLACE, request)
    }

    override fun enqueueRemove(photoId: String) {
        val request = OneTimeWorkRequestBuilder<FavoriteImageWorker>()
            .setInputData(workDataOf(KEY_PHOTO_ID to photoId, KEY_IS_SAVE to false))
            .build()
        workManager.enqueueUniqueWork("favorite-$photoId", ExistingWorkPolicy.REPLACE, request)
    }
}