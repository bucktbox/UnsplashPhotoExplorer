plugins {
    alias(libs.plugins.explorer.android.application)
    alias(libs.plugins.explorer.android.compose)
    alias(libs.plugins.explorer.android.hilt)
}

android {
    namespace = "com.kyeongilhan.photoexplorer"
    defaultConfig {
        applicationId = "com.kyeongilhan.photoexplorer"
        versionCode = 1
        versionName = "1.0.0"
    }
}

dependencies {
    implementation(projects.ui)
    implementation(projects.presentation)
    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.remote)
    implementation(projects.local)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.hilt.work)
}