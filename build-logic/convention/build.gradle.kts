import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.kyeongilhan.photoexplorer.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

// AGP 의 LibraryExtension 같은 타입을 씀
dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("jvmLibrary") {
            // 밖에서 보는 이름 (toml에 정의 되어 있다)
            id = "explorer.jvm.library"
            // 실제로는 이 클래스가 불린다
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvmHilt") {
            id = "explorer.jvm.hilt"
            implementationClass = "JvmHiltConventionPlugin"
        }
        register("androidLibrary") {
            id = "explorer.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "explorer.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "explorer.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidApplication") {
            id = "explorer.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}
