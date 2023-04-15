apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.pokemonDomain))

    // Retrofit
    "implementation"(Retrofit.retrofitGsonConverter)
}