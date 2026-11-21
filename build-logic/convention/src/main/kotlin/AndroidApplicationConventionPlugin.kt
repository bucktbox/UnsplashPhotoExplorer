import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure



/*
* ./gradlew 실행
 → 루트 settings.gradle.kts를 읽어요
 → pluginManagement의 includeBuild("build-logic")을 보고 build-logic을 먼저 구성하고 컴파일해요
     (build-logic/settings가 ../gradle/libs.versions.toml을 libs로 읽어요)
     (convention 모듈이 플러그인 6개의 id와 클래스 표를 가진 jar를 만들어요)
 → 루트 build.gradle.kts가 AGP·Kotlin·KSP·Hilt·Room을 apply false로 클래스패스에 올려요
 → 각 모듈의 plugins { alias(libs.plugins.explorer.…) }
     → 카탈로그가 id 문자열로 바꾸고 → included build에서 그 id를 찾아 → 클래스의 apply(project)를 실행해요
*
* */

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.application")
        pluginManager.apply("org.jetbrains.kotlin.android")

        extensions.configure<ApplicationExtension> {
            compileSdk = BuildConfigValues.COMPILE_SDK
            defaultConfig {
                minSdk = BuildConfigValues.MIN_SDK
                targetSdk = BuildConfigValues.TARGET_SDK
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = BuildConfigValues.JAVA_VERSION
                targetCompatibility = BuildConfigValues.JAVA_VERSION
            }
            buildTypes {
                getByName("release") {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro",
                    )
                    signingConfig = signingConfigs.getByName("debug")
                }
            }
            packaging {
                resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        configureKotlinAndroid()
    }
}
