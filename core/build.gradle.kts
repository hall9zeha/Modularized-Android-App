@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlinAndroid)
}

android{
    namespace = "com.barryzea.core"
    compileSdk = 34
    defaultConfig {
        minSdk = 24

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
dependencies{
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.viewmodel)
    implementation(libs.livedata)
    //Splash screen
    implementation(libs.splash.screen)
}
