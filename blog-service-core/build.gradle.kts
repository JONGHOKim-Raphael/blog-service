dependencies {
    val kotlinLoggingVersion = "2.0.11"
    implementation("io.github.microutils:kotlin-logging-jvm:${kotlinLoggingVersion}")

    val springbootVersion = "3.1.0"
    implementation("org.springframework.boot:spring-boot-starter:${springbootVersion}")

    val springdocVersion = "1.6.15"
    implementation("org.springdoc:springdoc-openapi-ui:${springdocVersion}")
}