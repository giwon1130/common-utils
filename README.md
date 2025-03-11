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
