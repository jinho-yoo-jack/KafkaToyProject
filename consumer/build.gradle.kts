dependencies {
    val kotlinVersion = "1.4.10"
    val kotlinxVersion = "1.4.1"

    runtimeOnly("mysql:mysql-connector-java")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5")
//    testImplementation("io.kotest:kotest-runner-junit5:5.0.3")
//    testImplementation("io.kotest:kotest-assertions-core")

}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}