import java.util.*
import java.io.*

plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

val localProperties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "local.properties")))
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "LIMIT", "\"${localProperties["LIMIT"]}\"")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "environment"

    productFlavors {

        create(BuildFlavors.DEV) {
            dimension = "environment"

            versionName = Android.versionNameDev
            applicationIdSuffix = ApplicationIdSuffix.DEV
            resValue("string", "app_name", "Phincon Challenge - Dev")

            buildConfigField("String", "BUILD_VARIANT", "\"DEV\"")
            buildConfigField("String", "BASE_POKEMON_URL", "\"${localProperties["BASE_POKEMON_URL_DEV"]}\"")
            buildConfigField("String", "BASE_PERSONAL_POKEMON_URL", "\"${localProperties["BASE_PERSONAL_POKEMON_URL_DEV"]}\"")
        }

        create(BuildFlavors.PROD) {
            dimension = "environment"

            versionName = Android.versionName
            applicationIdSuffix = ApplicationIdSuffix.PROD
            resValue("string", "app_name", "Phincon Challenge")

            buildConfigField("String", "BUILD_VARIANT", "\"PROD\"")
            buildConfigField("String", "BASE_POKEMON_URL", "\"${localProperties["BASE_POKEMON_URL_PROD"]}\"")
            buildConfigField("String", "BASE_PERSONAL_POKEMON_URL", "\"${localProperties["BASE_PERSONAL_POKEMON_URL_PROD"]}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    }

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        viewBinding = true
    }


    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {

    implementation(project(Modules.core))
    implementation(project(Modules.pokemonUiPokemon))
    implementation(project(Modules.pokemonUiMyPokemon))

    implementation(Androidx.core)
    implementation(Androidx.appCompat)
    implementation(Google.material)
    implementation(Androidx.constraint)
    implementation(Androidx.legacy)

    // Lifecycle Components
    implementation(Androidx.viewModel)
    implementation(Androidx.liveData)
    implementation(Androidx.viewModelSavedState)

    // Coroutine
    implementation(Kotlin.coroutineCore)
    implementation(Kotlin.coroutineAndroid)

    // Hilt
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

    // Navigation Component
    implementation(Jetpack.fragmentNavigation)
    implementation(Jetpack.uiNavigation)

    // Chucker
    implementation(Chucker.library)

    // Timber
    implementation(Timber.library)
}