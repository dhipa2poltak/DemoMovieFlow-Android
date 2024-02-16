plugins {
  id("java-library")
  id("org.jetbrains.kotlin.jvm")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
  implementation(Deps.coroutinesAndroid)

  testImplementation(Deps.jUnit)
  testImplementation(Deps.mockitoKotlin)
  testImplementation(Deps.coroutinesTest)
}
