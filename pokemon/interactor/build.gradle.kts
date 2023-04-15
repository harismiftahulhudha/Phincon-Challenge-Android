apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.pokemonDataSource))
    "implementation"(project(Modules.pokemonDomain))
}