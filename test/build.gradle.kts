@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.library")

}

android {
    namespace ="com.barryzea.test"
    compileSdk = 34
    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
dependencies{
    api(libs.androidx.test.core)
    //
    api(libs.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.junit)
    debugImplementation(libs.kotlinx.coroutines.test)
    debugImplementation(libs.robolectric.shadows)
    debugImplementation(libs.robolectric)
    api(libs.auto.service)

    implementation(project(":domain:models"))

}