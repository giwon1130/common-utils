plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.6" apply false
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.github.giwon1130"
version = "1.1.1"

repositories {
    mavenCentral() // JitPack이 의존성을 찾을 수 있도록 설정
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.6") // ✅ Spring Boot BOM 추가
    }
}

dependencies {
    // JWT (JSON Web Token) 라이브러리
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5") // JSON 처리

    // Spring Security & Web (Servlet API 사용을 위해 필요!)
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.6")
    implementation("org.springframework.boot:spring-boot-starter-security:3.3.6")
}

publishing {
    publications {
        create<MavenPublication>("jitpack") {
            from(components["java"])
            groupId = "com.github.giwon1130"
            artifactId = "common-utils"
            version = "1.1.1"

            // ✅ 소스 코드 포함 (JitPack에서 패키징 가능하도록)
            artifact(tasks.getByName("jar"))
        }

    }
}
