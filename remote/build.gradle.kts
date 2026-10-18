import java.util.Properties

plugins {
    alias(libs.plugins.explorer.android.library)
    alias(libs.plugins.explorer.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

val unsplashAccessKey: String = Properties().run {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use { load(it) }
    getProperty("UNSPLASH_ACCESS_KEY", "")
}

android {
    namespace = "com.kyeongilhan.photoexplorer.remote"
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        buildConfigField("String", "UNSPLASH_ACCESS_KEY", "\"$unsplashAccessKey\"")
    }
}

dependencies {
    implementation(projects.data)

    api(libs.retrofit)
    api(libs.okhttp)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)

    testImplementation(libs.okhttp.mockwebserver)
}