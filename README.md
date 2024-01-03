# Modularized-Android-App
<img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/core/src/main/res/mipmap-xxxhdpi/ic_launcher.webp"  alt="drawing" width="24%" height="24%"/>

Simple notepad app using modularization architecture with Android kotlin.

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/hall9zeha/Modularized-Android-App/blob/main/README.md) [![es](https://img.shields.io/badge/lang-es-yellow.svg)](https://github.com/hall9zeha/Modularized-Android-App/blob/main/README.es.md)




## Modules :card_file_box:
* Abstraction
* App
* Core
* Data
* Domain
    - models
* Feature
    - bookmark
    - onBoarding
* Navigation
* Test

## Requirements :gear:
* Java jdk 17
* Android sdk 34
## Project status
![Badge Finished](https://img.shields.io/badge/STATUS-%20FINISHED-green)

## Download demo 📂 [click here](https://github.com/hall9zeha/Modularized-Android-App/raw/main/docs/demo/notepad-release.apk)

## It was used :wrench:
The project uses a central catalog of versions in which the required dependencies are added and shared among the different modules, improving its scalability and maintenance.
[catalog versions file](https://github.com/hall9zeha/Modularized-Android-App/blob/main/gradle/libs.versions.toml)
* [Arquitectura MVVM](https://developer.android.com/jetpack/guide)
* [Clean code](https://developer.android.com/topic/architecture)

Algunas librerías usadas en el proyecto:

* [Data-store-preferences](https://developer.android.com/topic/libraries/architecture/datastore)
* [Flex box](https://android-developers.googleblog.com/2017/02/build-flexible-layouts-with.html)
* [Glide](https://developer.android.com/training/dependency-injection/hilt-android)
* [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* [Kotlin coroutines](https://developer.android.com/kotlin/coroutines)
* [Lottie-Animations](https://lottiefiles.com/blog/working-with-lottie/getting-started-with-lottie-animations-in-android-app)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Navigation-component](https://developer.android.com/guide/navigation/navigation-getting-started)
* [Roboelectric](https://robolectric.org/)
* [Room](https://developer.android.com/jetpack/androidx/releases/room?gclid=EAIaIQobChMIh-Hoi7C_-gIVRxXUAR2kZAAsEAAYASAAEgJnivD_BwE&gclsrc=aw.ds)
* [Android-splash-screen](https://developer.android.com/develop/ui/views/launch/splash-screen)
* [ViewModel](https://developer.android.com/jetpack/androidx/releases/lifecycle)
* [ViewPager2](https://developer.android.com/training/animation/screen-slide-2)
* [ColorPickerView](https://github.com/skydoves/ColorPickerView)

## Database relational model

<img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/db_graph.png"  alt="drawing" width="70%" height="70%"/>

## Features :memo:
* Show step-by-step presentation when you first open the application 
* Create, modify and delete notes
* Create, delete bookmarks
* Add bookmarks to note
* Filter notes by bookmarks
## Screenshots :framed_picture:

||||
|--|--|--|
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen1.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen2.jpg" alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen3.jpg"  alt="drawing" width="70%" height="70%"/></p>
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen4.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen5.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen6.jpg"  alt="drawing" width="70%" height="70%"/></p>
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen7.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen8.jpg"  alt="drawing" width="70%" height="70%"/></p>|

