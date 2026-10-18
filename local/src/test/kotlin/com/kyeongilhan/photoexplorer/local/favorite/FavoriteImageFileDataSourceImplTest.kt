package com.kyeongilhan.photoexplorer.local.favorite

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class FavoriteImageFileDataSourceImplTest {
    @get:Rule
    val temporaryFolder = TemporaryFolder()

    private lateinit var dataSource: FavoriteImageFileDataSourceImpl

    @Before
    fun setUp() {
        dataSource = FavoriteImageFileDataSourceImpl(temporaryFolder.root, Dispatchers.Unconfined)
    }

    @Test
    fun testFileSave() = runTest {
        // 저장 후 임시 파일 없이 최종 파일만 남음
        val path = dataSource.save("abc", byteArrayOf(1, 2, 3))
        val file = File(path)
        assertThat(file.readBytes()).isEqualTo(byteArrayOf(1, 2, 3))
    }
}