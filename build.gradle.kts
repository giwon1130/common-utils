plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.github.giwon1130"
version = "1.1.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
}

publishing {
    publications {
        create<MavenPublication>("jitpack") {
            from(components["java"]) // 중복 jar 제거
            groupId = "com.github.giwon1130"
            artifactId = "common-utils"
            version = "1.1.4"
        }
    }
}
