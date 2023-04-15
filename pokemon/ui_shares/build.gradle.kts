apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    // Modules
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.pokemonDomain))
    "implementation"(project(Modules.pokemonInteractor))

    // Glide
    "implementation"(Glide.glide)
    "kapt"(Glide.glideCompiler)

    // Paging
    "implementation"(Paging.paging)
}