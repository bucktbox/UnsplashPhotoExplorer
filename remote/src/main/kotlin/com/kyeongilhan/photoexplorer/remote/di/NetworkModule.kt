package com.kyeongilhan.photoexplorer.remote.di

import javax.inject.Qualifier
import com.kyeongilhan.photoexplorer.data.photo.datasource.ImageRemoteDataSource
import com.kyeongilhan.photoexplorer.data.photo.datasource.PhotoRemoteDataSource
import com.kyeongilhan.photoexplorer.remote.BuildConfig
import com.kyeongilhan.photoexplorer.remote.image.ImageRemoteDataSourceImpl
import com.kyeongilhan.photoexplorer.remote.interceptor.AuthInterceptor
import com.kyeongilhan.photoexplorer.remote.photo.PhotoRemoteDataSourceImpl
import com.kyeongilhan.photoexplorer.remote.api.UnsplashApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageHttpClient

@Module
@InstallIn(SingletonComponent::class)
internal interface RemoteModule {
    @Binds
    fun bindPhotoRemoteDataSource(impl: PhotoRemoteDataSourceImpl): PhotoRemoteDataSource

    @Binds
    fun bindImageRemoteDataSource(impl: ImageRemoteDataSourceImpl): ImageRemoteDataSource

    companion object {
        private const val TIMEOUT_SECONDS = 20L
        private const val CONTENT_TYPE_JSON = "application/json"

        @Provides
        @Singleton
        fun provideJson(): Json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

        @Provides
        @Singleton
        @ImageHttpClient
        fun provideImageHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

        @Provides
        @Singleton
        @ApiHttpClient
        fun provideApiHttpClient(@ImageHttpClient baseClient: OkHttpClient): OkHttpClient = baseClient.newBuilder()
            .addInterceptor(AuthInterceptor(BuildConfig.UNSPLASH_ACCESS_KEY))
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BASIC
                            redactHeader(AuthInterceptor.HEADER_AUTHORIZATION)
                        },
                    )
                }
            }
            .build()

        @Provides
        @Singleton
        fun provideUnsplashApiService(
            @ApiHttpClient okHttpClient: OkHttpClient,
            json: Json,
        ): UnsplashApiService = Retrofit.Builder()
            .baseUrl(UnsplashApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE_JSON.toMediaType()))
            .build()
            .create(UnsplashApiService::class.java)
    }
}