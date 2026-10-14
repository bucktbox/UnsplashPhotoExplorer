plugins {
    alias(libs.plugins.explorer.jvm.library)
}

dependencies {
    api(projects.dataResource)
    implementation(projects.component)
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core)
}