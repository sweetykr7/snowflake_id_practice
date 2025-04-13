
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    java
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

group = "me.nettee"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.springframework.boot:spring-boot-autoconfigure:3.4.4")
    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa:3.4.4")

    testImplementation("org.springframework.boot:spring-boot-starter-web:3.4.4")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.4")


    testRuntimeOnly("org.postgresql:postgresql:42.7.4")
    testImplementation("org.flywaydb:flyway-database-postgresql:10.20.1")

    // test tool
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation(kotlin("script-runtime"))
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
}

kotlin{
    sourceSets {
        test {
            kotlin.srcDirs(listOf("src/test/kotlin"))
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}


//plugins {
//    java
//    id("org.springframework.boot") version "3.4.4"
//    id("io.spring.dependency-management") version "1.1.7"
//}
//
//group = "me.nettee"
//version = "0.0.1-SNAPSHOT"
//
//java {
//    toolchain {
//        languageVersion = JavaLanguageVersion.of(21)
//    }
//}
//
//repositories {
//    mavenCentral()
//}
//
//dependencies {
//    implementation("org.springframework.boot:spring-boot-starter")
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//}
//
//tasks.withType<Test> {
//    useJUnitPlatform()
//}
