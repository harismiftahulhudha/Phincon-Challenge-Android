apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    // Modules
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.pokemonDomain))
    "implementation"(project(Modules.pokemonInteractor))

    // Lifecycle Components
    "implementation"(Androidx.viewModel)
    "implementation"(Androidx.liveData)
    "implementation"(Androidx.viewModelSavedState)

    // Navigation Component
    "implementation"(Jetpack.fragmentNavigation)
    "implementation"(Jetpack.uiNavigation)
}