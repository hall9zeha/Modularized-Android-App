@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlinAndroid)
}
android{
    namespace = "com.barryzea.data"
    compileSdk = 34

    kotlinOptions {
        jvmTarget = "17"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies{
    implementation(project(":domain:models"))
}