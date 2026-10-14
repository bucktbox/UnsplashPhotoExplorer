plugins {
    alias(libs.plugins.explorer.android.library)
    alias(libs.plugins.explorer.android.compose)
}

android {
    namespace = "com.kyeongilhan.photoexplorer.ui"
}

dependencies {
    implementation(projects.presentation)
    implementation(libs.androidx.compose.material3)
}