package com.kyeongilhan.photoexplorer.local.favorite

import com.kyeongilhan.photoexplorer.data.favorite.datasource.FavoriteImageFileDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

internal class FavoriteImageFileDataSourceImpl(
    rootDir: File,
    private val ioDispatcher: CoroutineDispatcher,
) : FavoriteImageFileDataSource {
    private val directory = File(rootDir, DIRECTORY_NAME)

    // 임시 파일에 다 쓰고 나서 진짜 이름으로 바꿈
    override suspend fun save(photoId: String, bytes: ByteArray): String = withContext(ioDispatcher) {
        if (!directory.isDirectory && !directory.mkdirs()) throw IOException("cannot create $directory")

        val target = getFileName(photoId)
        val temp = getTempFileName(photoId)

        temp.writeBytes(bytes)

        // 저장 다 하고 이름 바꾸기 (안되면 삭제..)
        if (!temp.renameTo(target)) {
            target.delete()
            throw IOException("cannot move $temp to $target")
        }

        target.absolutePath
    }

    override suspend fun delete(photoId: String) {
        withContext(ioDispatcher) {
            getFileName(photoId).delete()
            getTempFileName(photoId).delete()
        }
    }

    private fun getFileName(photoId: String): File = File(directory, "${photoId}$EXTENSION")

    private fun getTempFileName(photoId: String): File = File(directory, "${photoId}$EXTENSION$TEMP_SUFFIX")

    private companion object {
        const val DIRECTORY_NAME = "favorites"
        const val EXTENSION = ".webp"
        const val TEMP_SUFFIX = ".tmp"
    }
}