# Modularized-Android-App
Aplicación sencilla de bloc de notas que usa una arquitectura de modularizacion con Android kotlin 

## Módulos :card_file_box:
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

## Pre-requisitos :gear:
* Java jdk 17
* Android sdk 34
## Estado del proyecto
![Badge Terminado](https://img.shields.io/badge/STATUS-%20TERMINADO-green)
## Se utilizó :wrench:
El proyecto usa un catalogo central de versiones en el cual se van agregando las dependencias que se requieran y se comparte a los diferentes módulos, mejorando la escalabilidad y el mantenimiento del mismo.
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


## Funciones :memo:
* Mostrar presentación paso a paso al abrir por primera vez la aplicación 
* Crear, modificar y eliminar notas
* crear, eliminar marcadores
* Adjuntar marcadores a nota
* Filtrar notas por marcadores
## Capturas :framed_picture:
|on boarding|||
||||
|--|--|--|
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen1.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen2.jpg" alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen3.jpg"  alt="drawing" width="70%" height="70%"/></p>
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen4.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen5.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen6.jpg"  alt="drawing" width="70%" height="70%"/></p>
|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen7.jpg"  alt="drawing" width="70%" height="70%"/></p>|<p align="center" width="70%"><img src="https://github.com/hall9zeha/Modularized-Android-App/blob/main/docs/images/screen8.jpg"  alt="drawing" width="70%" height="70%"/></p>|

