package com.kyeongilhan.photoexplorer.remote.photo

import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.remote.api.UnsplashApiService
import com.kyeongilhan.photoexplorer.remote.photo.mapper.toEntity
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class UnsplashApiServiceTest {
    private lateinit var server: MockWebServer
    private lateinit var service: UnsplashApiService

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        val json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
        service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(UnsplashApiService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @OptIn(InternalSerializationApi::class)
    @Test
    fun testGetLatestPhotos() = runTest {
        server.enqueue(MockResponse().setBody(readJson("json/photos_latest.json")))

        val photos = service.getLatestPhotos(page = 3)

        val url = server.takeRequest().requestUrl!!
        assertThat(url.queryParameter("page")).isEqualTo("3")
        assertThat(url.queryParameter("per_page")).isEqualTo("30")
        assertThat(url.queryParameter("order_by")).isEqualTo("latest")
        assertThat(photos).isNotEmpty()
    }

    @OptIn(InternalSerializationApi::class)
    @Test
    fun testGetPhotoDetail() = runTest {
        server.enqueue(MockResponse().setBody(readJson("json/photo_detail.json")))

        val entity = service.getPhotoDetail("j9Vp6hAZDV8").toEntity()

        assertThat(entity.exifName).isEqualTo("Canon, EOS R5m2")
        assertThat(entity.location).isEqualTo("Pennsylvania, USA")
        assertThat(entity.tags).contains("woman")
    }

    private fun readJson(path: String): String =
        requireNotNull(javaClass.classLoader?.getResource(path)) { "$path 없음" }.readText()
}