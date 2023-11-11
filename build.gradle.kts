@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
/*    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false
    id("com.android.library") version "8.1.1" apply false*/
    alias(libs.plugins.android) apply(false)
    alias(libs.plugins.kotlinAndroid) apply(false)
    alias(libs.plugins.jvm) apply(false)
    alias(libs.plugins.library) apply(false)
    alias(libs.plugins.kapt) apply(false)
    alias(libs.plugins.hilt) apply(false)
    alias(libs.plugins.kotlin.parcelize) apply(false)
}