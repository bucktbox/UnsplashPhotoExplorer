plugins {
    alias(libs.plugins.explorer.android.library)
    alias(libs.plugins.explorer.android.hilt)
}

android {
    namespace = "com.kyeongilhan.photoexplorer.presentation"
}

dependencies {
    // 화면 모델(PhotoCardUiModel)에 domain의 Photo가 들어가서 ui도 domain 타입을 봐야 함
    api(projects.domain)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.paging.common)

    testImplementation(libs.androidx.paging.testing)
}