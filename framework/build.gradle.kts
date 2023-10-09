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
  compileSdk = 33

  defaultConfig {
    minSdk = 21

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
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
}

dependencies {

  implementation(project(":domain"))
  implementation(project(":data"))

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.9.0")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

  implementation("com.google.dagger:hilt-android:2.44")
  kapt("com.google.dagger:hilt-compiler:2.44")

  implementation("com.squareup.okhttp3:okhttp:4.9.3")
  implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("com.google.code.gson:gson:2.9.0")

  implementation("androidx.room:room-runtime:2.5.0")
  kapt("androidx.room:room-compiler:2.5.0")
  implementation("androidx.room:room-ktx:2.5.0")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

  implementation("androidx.paging:paging-runtime:3.0.0-alpha04")

  implementation("com.squareup.picasso:picasso:2.8")
}
