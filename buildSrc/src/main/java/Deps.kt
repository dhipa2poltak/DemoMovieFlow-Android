object Deps {
  val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
  val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
  val material = "com.google.android.material:material:${Versions.material}"
  val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
  val jUnit = "junit:junit:${Versions.jUnit}"
  val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
  val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlin}"
  val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"

  val jUnitExt = "androidx.test.ext:junit:${Versions.jUnitExt}"
  val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

  const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragment}"
  const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationUi}"

  const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
  const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hiltCompiler}"

  const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
  const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
  const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
  const val gson = "com.google.code.gson:gson:${Versions.gson}"

  const val roomRuntime = "androidx.room:room-runtime:${Versions.roomRuntime}"
  const val roomCompiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
  const val room = "androidx.room:room-ktx:${Versions.room}"

  const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
  const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.pagingRuntime}"
  const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
  const val annotation = "androidx.annotation:annotation:${Versions.annotation}"

  const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
  const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModel}"
}
