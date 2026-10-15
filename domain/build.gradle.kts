plugins {
    alias(libs.plugins.explorer.jvm.library)
}

dependencies {
    api(projects.dataResource)
    api(projects.component)
    api(libs.javax.inject)
    api(libs.kotlinx.coroutines.core)
}