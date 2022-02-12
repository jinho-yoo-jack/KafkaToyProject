repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

dependencies {
    val kotlinVersion = "1.4.10"
    val kotlinxVersion = "1.4.1"

    runtimeOnly("mysql:mysql-connector-java")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5")

}
