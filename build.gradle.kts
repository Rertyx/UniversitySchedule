plugins {
    java
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("idea")

}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

group = "ru.dimov"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.liquibase:liquibase-core")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("com.google.guava:guava:33.1.0-jre")
    implementation("one.util:streamex:0.8.2")
    implementation("org.telegram:telegrambots-springboot-longpolling-starter:7.2.1")
    implementation("org.telegram:telegrambots-client:7.2.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
