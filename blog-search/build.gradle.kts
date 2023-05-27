dependencies {
    val springbootVersion = "3.1.0"
    implementation("org.springframework.boot:spring-boot-starter:${springbootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${springbootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-webflux:${springbootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${springbootVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${springbootVersion}") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    val h2Version = "2.1.214"
    implementation("com.h2database:h2:${h2Version}")

    val junitVersion = "5.8.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")

    val kotestVersion = "5.5.5"
    testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
    testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
//    testImplementation("io.kotest:kotest-extensions-mockserver:${kotestVersion}")

    val kotestSpringExtensionVersion = "1.1.2"
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${kotestSpringExtensionVersion}")

    val mockkVersion = "1.13.4"
    testImplementation("io.mockk:mockk:${mockkVersion}")

    val mockserverVersion = "5.15.0"
    testImplementation("org.mock-server:mockserver-core:${mockserverVersion}")
    testImplementation("org.mock-server:mockserver-client-java:${mockserverVersion}")

    val springdocVersion = "2.1.0"
    implementation("org.springdoc:springdoc-openapi-starter-common:${springdocVersion}")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocVersion}")

    // in order to import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
    // SEE: - https://github.com/FasterXML/jackson-module-kotlin
    //      - https://docs.spring.io/spring-boot/docs/2.7.9/reference/html/dependency-versions.html
    val jacksonKotlinModuleVersion = "2.13.5"   // Spring Boot 2.7.9 uses Jackson 2.13.5
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonKotlinModuleVersion}")

    // QueryDSL
    //  - https://l4279625.tistory.com/146
    val querydslVersion = "5.0.0"
    implementation("com.querydsl:querydsl-jpa:${querydslVersion}:jakarta")
    kapt("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
}