plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.8" apply false
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.backend"
version = "0.0.1-SNAPSHOT"
description = "zeronsoftn-problem"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

// 모든 프로젝트(루트 + 서브프로젝트)에 공통으로 적용되는 설정
allprojects {
    repositories {
        mavenCentral()
    }
}

// 루트 프로젝트는 소스 컴파일하지 않음 (루트는 라이브러리 프로젝트가 아님)
tasks.named("compileKotlin") {
    enabled = false
}
tasks.named("compileJava") {
    enabled = false
}
tasks.named("processResources") {
    enabled = false
}
tasks.named("compileTestKotlin") {
    enabled = false
}
tasks.named("compileTestJava") {
    enabled = false
}
tasks.named("processTestResources") {
    enabled = false
}
tasks.named("test") {
    enabled = false
}

// 서브프로젝트(user-service, notification-service)에만 적용되는 설정
subprojects {
    // 플러그인 적용
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    
    // Kotlin 컴파일러 옵션
    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }
    
    // Java 버전 설정
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }
    
    // 테스트 설정
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
