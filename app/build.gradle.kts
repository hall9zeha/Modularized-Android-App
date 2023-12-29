@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)

}

android {
    namespace = "com.barryzea.modularizedapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.barryzea.modularizedapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    //Splash screen
    implementation(libs.splash.screen)
    kaptTest(libs.hilt.compiler)
    //Color picker
    implementation(libs.color.picker)
    //Fex box
    implementation(libs.flex.box)

    debugImplementation(libs.kotlinx.coroutines.test)
    debugImplementation(libs.robolectric)
    // Modules
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":domain:models"))
    implementation(project(":data"))
    implementation(project(":abstraction"))
    implementation(project(":feature"))
    implementation(project(":feature:bookmark"))
    implementation(project(":feature:onBoarding"))
    implementation(project(":navigation"))
    implementation(project(":test"))



}