plugins {
  id("java-library")
  id("org.jetbrains.kotlin.jvm")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
  implementation(project(":domain"))

  implementation("com.google.code.gson:gson:2.9.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

  implementation("androidx.annotation:annotation:1.7.0")
}
