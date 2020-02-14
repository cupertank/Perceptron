plugins {
    java
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.serialization") version "1.3.61"
}

group = "cupertank.perceptron"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
    maven("https://dl.bintray.com/kyonifer/maven")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")
    implementation("com.kyonifer:koma-core-ejml:0.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

}