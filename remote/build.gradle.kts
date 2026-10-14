plugins {
    alias(libs.plugins.explorer.android.library)
    alias(libs.plugins.explorer.android.hilt)
}

android {
    namespace = "com.kyeongilhan.photoexplorer.remote"
}

dependencies {
    implementation(projects.data)
}