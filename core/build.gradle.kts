@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kapt)
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
    //Data store preferences
    implementation(libs.data.store)
    //Dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    //Glide
    implementation(libs.glide)
    kapt(libs.glide.compiler)
    //modules
    implementation(project(":navigation"))


}
