plugins {
    alias(libs.plugins.explorer.jvm.library)
    alias(libs.plugins.explorer.jvm.hilt)
}

dependencies {
    api(projects.domain)
}