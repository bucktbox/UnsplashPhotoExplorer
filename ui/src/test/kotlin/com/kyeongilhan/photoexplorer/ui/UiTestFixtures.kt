package com.kyeongilhan.photoexplorer.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider
import com.kyeongilhan.photoexplorer.domain.photo.model.Author
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo

// UI 테스트에서 같이 쓰는 것들

// 화면에 보이는 문구는 strings.xml 에서 읽는다 (문구가 바뀌어도 테스트가 따라가도록)
fun string(@StringRes id: Int, vararg args: Any): String =
    ApplicationProvider.getApplicationContext<Context>().getString(id, *args)

// 없는 파일 경로. 이미지를 파일에서 읽게 해서 테스트가 네트워크로 나가지 않게 한다
const val MISSING_IMAGE_PATH = "/no/such/favorite.jpg"

fun testPhoto(
    id: String = "p1",
    authorName: String = "홍길동",
    username: String = "gildong",
) = Photo(
    id = id,
    width = 300,
    height = 200,
    description = null,
    color = "#336699",
    imageUrl = "",
    author = Author(name = authorName, username = username, profileImageUrl = null),
    downloadLocation = "",
)
