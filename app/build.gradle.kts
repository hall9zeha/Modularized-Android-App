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
            isMinifyEnabled = false
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    //Splash screen
    implementation(libs.splash.screen)
    kaptTest("com.google.dagger:hilt-android-compiler:2.48.1")


    // Modules
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":domain:models"))
    implementation(project(":data"))
    implementation(project(":abstraction"))
    implementation(project(":test"))
    //implementation(libs.libraries.retrofit-core)

}