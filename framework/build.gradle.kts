import java.io.FileInputStream
import java.util.Properties

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
  keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("com.google.dagger.hilt.android")
  id("kotlin-kapt")
}

android {
  namespace = "com.dpfht.android.demomovieflow.framework"
  compileSdk = ConfigData.compileSdkVersion

  defaultConfig {
    minSdk = ConfigData.minSdkVersion

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

      buildConfigField("String", "ACCESS_TOKEN", "\"${keystoreProperties["accessToken"] as String}\"")
    }
    debug {
      buildConfigField("String", "ACCESS_TOKEN", "\"${keystoreProperties["accessToken"] as String}\"")
    }
  }
  buildFeatures {
    buildConfig = true
    viewBinding = true
    dataBinding = true
  }
  compileOptions {
    sourceCompatibility = ConfigData.sourceCompatibilityVersion
    targetCompatibility = ConfigData.targetCompatibilityVersion
  }
  kotlinOptions {
    jvmTarget = ConfigData.jvmTargetVersion
  }
}

dependencies {

  implementation(project(":domain"))
  implementation(project(":data"))

  implementation(Deps.coreKtx)
  implementation(Deps.appCompat)
  implementation(Deps.material)
  testImplementation(Deps.jUnit)
  testImplementation(Deps.mockitoKotlin)
  testImplementation(Deps.mockitoInline)
  testImplementation(Deps.coroutinesTest)
  testImplementation(Deps.mockWebServer)
  testImplementation(Deps.robolectric)
  testImplementation(Deps.coreTesting)
  testImplementation(Deps.coreKtxTesting)

  androidTestImplementation(Deps.jUnitExt)
  androidTestImplementation(Deps.espresso)

  implementation(Deps.hilt)
  kapt(Deps.hiltCompiler)

  implementation(Deps.okHttp)
  implementation(Deps.loggingInterceptor)
  implementation(Deps.gsonConverter)
  implementation(Deps.gson)

  implementation(Deps.roomRuntime)
  kapt(Deps.roomCompiler)
  implementation(Deps.room)

  implementation(Deps.coroutinesAndroid)
  implementation(Deps.pagingRuntime)
  implementation(Deps.picasso)
}
