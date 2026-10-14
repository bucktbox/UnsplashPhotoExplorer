plugins {
    alias(libs.plugins.explorer.android.library)
    alias(libs.plugins.explorer.android.hilt)
}

android {
    namespace = "com.kyeongilhan.photoexplorer.presentation"
}

dependencies {
    implementation(projects.domain)
    implementation(projects.component)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}