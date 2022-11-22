plugins {
    kotlin("jvm") version "1.7.20"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //implementation(kotlin("stdlib-jdk8"))
    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.arrow-kt:arrow-core:1.1.3")
}
