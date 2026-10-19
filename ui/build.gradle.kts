plugins {
    alias(libs.plugins.explorer.android.library)
    alias(libs.plugins.explorer.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kyeongilhan.photoexplorer.ui"
}

dependencies {
    implementation(projects.presentation)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}