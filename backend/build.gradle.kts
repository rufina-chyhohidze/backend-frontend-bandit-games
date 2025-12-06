import org.gradle.kotlin.dsl.testRuntimeOnly

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
    maven("https://repo.spring.io/milestone")
}


dependencies {
    // WEB, SECURITY & OAUTH2
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // DATA (MongoDB)
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    // NOTE: spring-boot-starter-data-jpa is commented out, which is correct since you are using MongoDB.

    // MODULITH & AMQP (Events)
    implementation("org.springframework.modulith:spring-modulith-starter-core:1.4.1")
    implementation("org.springframework.modulith:spring-modulith-events-api:1.4.1")
    implementation("org.springframework.modulith:spring-modulith-events-amqp:1.4.1")
    implementation("org.springframework.boot:spring-boot-starter-amqp") // Needed for AMQP connection pooling

    // EXTERNAL APIs
    implementation("com.stripe:stripe-java:25.0.0")
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.modulith:spring-modulith-starter-jpa:1.4.1")

    //SECURITY
    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation ("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // WebClient
    implementation("org.springframework.boot:spring-boot-starter-webflux")


    // TESTING
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test:1.1.4")
    testImplementation("org.springframework.amqp:spring-rabbit-test")

    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:rabbitmq")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:postgresql")
    
    runtimeOnly("org.postgresql:postgresql")
    testRuntimeOnly ("com.h2database:h2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.testcontainers:junit-jupiter:1.20.0")
    testImplementation("org.testcontainers:mongodb:1.20.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks.named<Test>("test") {
    useJUnitPlatform()
}
