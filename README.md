# kmp-base
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.codinux.kotlin/kmp-base/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.codinux.kotlin/kmp-base)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Basic Kotlin Multiplatform functions and classes missing in the Kotlin StdLib.


## Setup

### Gradle

```
implementation("net.codinux.kotlin:kmp-base:1.5.1")

implementation("net.codinux.kotlin:kmp-base-text:1.5.1")
```

### Maven

```xml
<dependency>
   <groupId>net.codinux.kotlin</groupId>
   <artifactId>kmp-base-jvm</artifactId>
   <version>1.5.1</version>
</dependency>
```


## Usage

### Getting the platform type

```kotlin
println("Running on platform ${Platform.type}") // returns the type of the platform like JVM, iOS, Linux, JsBrowser, ...

Platform.isAndroidOrIOS // see also the other Platform.isXyz convenience methods to determine platform type
Platform.isNative
Platform.isJavaScript
```

### Get system information

```kotlin
println("Platform line separator: ${Platform.lineSeparator}")
println("Platform file separator: ${Platform.fileSeparator}")

println("OS name: ${Platform.osName}")
println("OS version: ${Platform.osVersion}")
println("CPU architecture: ${Platform.cpuArchitecture}")
```

### Get information about environment

```kotlin
val environment = Environment()

println("Environment variables:")
environment.variables.forEach { (name, value) ->
    println("$name: $value")
}

// not very reliable but a good hint on most platforms
println("Is running in debug mode: ${environment.isRunningInDebugMode}")
println("Is running tests: ${environment.isRunningTests}")
```

### String extensions

```kotlin
null.isNotNullOrBlank()

"test-string".indexOfOrNull("fun") // returns `null` instead of `-1` if string is not found

"another-test-string".indicesOf("-") // returns [7, 12]

"another-test-string".count("-") // returns 2

"test-string".ofMaxLength(4) // returns a string of max length 4 if the string is longer then 4, otherwise the original string

// see also the other .substringXyz() methods
"test".substringBeforeOrNull("fun") // returns `null` instead of `-1` if string "fun" is not found
"test".substringAfterLastOrNull("fun") // returns `null` instead of `-1` if string "fun" is not found
```


### kmp-base-text

#### Charsets

```kotlin
// standard charsets:
Charsets.UTF8 // also .UTF16_LE, .US_ASCII, .ISO_8859_1, ... 

// charsets available on platform:
Charset.availableCharsets

// charset for name
Charset.forName("UTF-8")
```


## License
```
Copyright 2023 dankito

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
