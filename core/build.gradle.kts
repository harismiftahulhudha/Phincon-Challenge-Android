apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    // Retrofit
    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.retrofitGsonConverter)
    "implementation"(Retrofit.retrofitScalarsConverter)
    
    // OkHttp3
    "implementation"(OkHttp3.library)

    // Room
    "implementation"(Room.room)
    "kapt"(Room.roomCompiler)
    "implementation"(Room.roomKtx)

    // Chucker
    "implementation"(Chucker.library)

    // Paging
    "implementation"(Paging.paging)

    // Navigation Component
    "implementation"(Jetpack.fragmentNavigation)
    "implementation"(Jetpack.uiNavigation)
}