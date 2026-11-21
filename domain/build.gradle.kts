plugins {
    // id("explorer.jvm.library") 하고 같다
    alias(libs.plugins.explorer.jvm.library)
}

dependencies {
    api(projects.dataResource)
    api(libs.javax.inject)
    api(libs.kotlinx.coroutines.core)
    api(libs.androidx.paging.common)
}