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

    // Compose UI 테스트 (기기 없이 JVM 에서 Robolectric 으로 실행)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.androidx.compose.ui.test.junit4)
    // createComposeRule 이 띄우는 빈 Activity 의 매니페스트
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}