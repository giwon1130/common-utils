제공하는 기능 정보
1. 공통 사용자 엔티티
2. JWT 관련 Util
   3. JWT 토큰 생성 (createToken)
      JWT 토큰 검증 (validateToken)
      토큰에서 사용자 정보 추출 (getUsernameFromToken)
      JWT 관련 예외 공통 처리


# Common Utils

This repository contains shared utilities for multiple projects, including authentication, exception handling, logging, and general utilities.

## Installation

Add the following dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.your-username:common-utils:1.0.0")
}
```

Also, make sure to include JitPack in your repositories:

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}
```

## Features
- **Authentication:** JWT utilities and password encoding.
- **Exception Handling:** Global exception handler.
- **Logging:** Common logging utilities.
- **Utilities:** Date formatting and response utilities.

## Usage
Import the required classes in your project:

```kotlin
import com.example.common.utils.DateUtils

fun main() {
    println(DateUtils.nowFormatted())
}
```

## License
MIT License
