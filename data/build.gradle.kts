@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.library)
    //alias(libs.plugins.jvm)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kapt)
}
android{
    namespace = "com.barryzea.data"
    compileSdk = 34
    defaultConfig {
       minSdk = 24

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
}

dependencies{

    implementation(project(":domain:models"))
    implementation(project(":abstraction"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    api(libs.room.runtime)
    api(libs.room.ktx)
    kapt(libs.room.compiler)
    implementation(libs.activity.ktx)

}