plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.github.giwon1130"
version = "1.4.0"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.2") // Spring Boot BOM 적용
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // JWT (JSON Web Token) 라이브러리
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5") // JSON 처리

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // JPA & Database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.7.5")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0") // ✅ JAXB API 추가
    implementation("org.glassfish.jaxb:jaxb-runtime:4.0.0") // ✅ JAXB Runtime 추가

}

publishing {
    publications {
        create<MavenPublication>("jitpack") {
            from(components["java"])
            groupId = "com.github.giwon1130"
            artifactId = "common-utils"
            version = "1.4.0"
        }
    }
}
