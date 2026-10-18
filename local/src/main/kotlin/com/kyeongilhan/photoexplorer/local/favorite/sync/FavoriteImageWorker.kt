package com.kyeongilhan.photoexplorer.local.favorite.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kyeongilhan.photoexplorer.data.favorite.sync.FavoriteImageSaver
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CancellationException

@HiltWorker
class FavoriteImageWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val favoriteImageSaver: FavoriteImageSaver,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val photoId = inputData.getString(KEY_PHOTO_ID) ?: return Result.failure()
        val isSave = inputData.getBoolean(KEY_IS_SAVE, true)

        return try {
            if (isSave) favoriteImageSaver.save(photoId) else favoriteImageSaver.delete(photoId)
            Result.success()
        } catch (e: CancellationException) {
            throw e // 취소는 실패가 아님
        } catch (e: Exception) {
            // 3번까지 다시 시도
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }

    companion object {
        const val KEY_PHOTO_ID = "photo_id"
        const val KEY_IS_SAVE = "is_save"
    }
}