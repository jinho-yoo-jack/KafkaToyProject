import jdk.nashorn.internal.objects.NativeRegExp.test
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val querydslVersion: String by extra { "4.3.1" }
val coroutineVersion: String by extra { "1.3.9" }

plugins {
    val kotlinVersion = "1.4.10"
    val springBootVersion = "2.3.3.RELEASE"
    val springDependencyManagementVersion = "1.0.10.RELEASE"

    // PLUGIN: Language
    java

    // PLUGIN: Kotlin
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("plugin.allopen") version kotlinVersion apply false

//    id("io.kotest")

    // PLUGIN: Spring Boot
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version springDependencyManagementVersion

    // PLUGIN: Jib
    id("com.google.cloud.tools.jib") version "2.5.0" apply false

    // PLUGIN: Jooq
    id("nu.studer.jooq") version "5.0.2" apply false

    // PLUGIN: Test, Code Quality
    id("org.sonarqube") version "3.0"
    id("com.adarshr.test-logger") version "2.0.0"
    jacoco

    // PLUGIN: flyway
    id("org.flywaydb.flyway") version "6.5.5"
}


java.sourceCompatibility = JavaVersion.VERSION_1_8



allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "jinho.toyproject.kafka"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")

    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR8")
        }
    }


    dependencies {
        val kotlinVersion = "1.4.10"
        val kotlinxVersion = "1.4.1"

        /* Spring Boot Starter */
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        /* Spring Cloud */
        implementation("org.springframework.cloud:spring-cloud-starter-config")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

        // Circuit Breaker
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-hystrix")

        // Spring Cloud - Service Discovery
        implementation("org.springframework.cloud:spring-cloud-starter-zookeeper-discovery") {
            exclude("org.apache.zookeeper", "zookeeper")
        }
        implementation("org.apache.zookeeper:zookeeper:3.4.13") {
            exclude("org.slf4j", "slf4j-log4j12")
        }

        /* Kotlin */
        implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinxVersion")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        /* Kafka */
        implementation("org.springframework.kafka:spring-kafka")
        implementation("io.projectreactor.kafka:reactor-kafka")
        testImplementation("org.springframework.kafka:spring-kafka-test")

        /* For TestCode */
        testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")


    }


}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
