object Hilt {
    private const val hiltVersion = "2.45"
    const val gradleHilt = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"
    const val hiltTest = "com.google.dagger:hilt-android-testing:$hiltVersion"

    private const val hiltActivityVersion = "1.5.1"
    const val hiltActivity = "androidx.activity:activity-ktx:$hiltActivityVersion"

    private const val hiltFragmentVersion = "1.5.1"
    const val hiltFragment = "androidx.fragment:fragment-ktx:$hiltFragmentVersion"

    private const val hiltWorkManagerVersion = "1.0.0"
    const val hiltWorkManager = "androidx.hilt:hilt-work:$hiltWorkManagerVersion"

    private const val hiltCompilerAndroidXVersion = "1.0.0"
    const val hiltCompilerAndroidX = "androidx.hilt:hilt-compiler:$hiltCompilerAndroidXVersion"
}