plugins {
    alias(libs.plugins.explorer.jvm.library)
}

dependencies {
    api(projects.dataResource)
    api(libs.javax.inject)
    api(libs.kotlinx.coroutines.core)
    api(libs.androidx.paging.common)
}