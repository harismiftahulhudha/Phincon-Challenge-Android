apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    // Modules
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.pokemonDomain))
    "implementation"(project(Modules.pokemonInteractor))
    "implementation"(project(Modules.pokemonUiDetail))
    "implementation"(project(Modules.pokemonUiRenamePokemon))
    "implementation"(project(Modules.pokemonUiShares))

    // Lifecycle Components
    "implementation"(Androidx.viewModel)
    "implementation"(Androidx.liveData)
    "implementation"(Androidx.viewModelSavedState)

    // Navigation Component
    "implementation"(Jetpack.fragmentNavigation)
    "implementation"(Jetpack.uiNavigation)

    // Glide
    "implementation"(Glide.glide)
    "kapt"(Glide.glideCompiler)

    // Paging
    "implementation"(Paging.paging)
}