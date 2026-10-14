plugins {
    alias(libs.plugins.explorer.android.library)
    alias(libs.plugins.explorer.android.hilt)
}

android {
    namespace = "com.kyeongilhan.photoexplorer.local"
}

dependencies {
    implementation(projects.data)
}