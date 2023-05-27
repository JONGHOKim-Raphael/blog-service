import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.20-RC"
    kotlin("plugin.spring") version "1.8.20-RC"
    kotlin("plugin.jpa") version "1.8.20-RC"
    kotlin("kapt") version "1.8.20-RC"
    id("io.kotest") version "0.3.8"
}

allprojects {
    group = "jongho"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "application")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "io.kotest")

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    allOpen {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }

    noArg {
        annotation("javax.persistence.Entity")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

rootProject.springBoot {
    mainClass.set("jongho.blog.blogservice.BlogServiceApplicationKt")
}

rootProject.dependencies {
    implementation(project(":blog-service-core"))
    implementation(project(":blog-search"))
}

project(":blog-service-core") {
    springBoot {
        mainClass.set("jongho.blog.blogservice.BlogServiceApplicationKt")
    }
}

project(":blog-search") {
    springBoot {
        mainClass.set("jongho.blog.blogsearch.BlogSearchApplicationKt")
    }
}