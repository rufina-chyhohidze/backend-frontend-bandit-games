plugins {
    java
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "kdg.be.banditgames"
version = "0.0.1-SNAPSHOT"
description = "backend"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

val springModulithVersion = "1.4.1"

dependencies {
    // Core Spring Boot starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    
    //uncomment for database use
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    runtimeOnly("org.postgresql:postgresql")
    
    implementation("org.springframework.modulith:spring-modulith-starter-core:${springModulithVersion}")
    //uncomment for event-driven architecture
//    implementation("org.springframework.modulith:spring-modulith-events-api:${springModulithVersion}")
//    implementation("org.springframework.modulith:spring-modulith-events-amqp:${springModulithVersion}")
    
    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
