plugins {
    alias(libs.plugins.explorer.jvm.library)
    alias(libs.plugins.explorer.jvm.hilt)
}

dependencies {
    implementation(projects.domain)
    implementation(projects.component)
    implementation(libs.kotlinx.coroutines.core)
}