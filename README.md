![Abele](abele.png)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/b80349082cef4219bfd88687cb81fb50)](https://www.codacy.com/gh/FitPuli/abele?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=FitPuli/abele&amp;utm_campaign=Badge_Grade) [![Build Status](https://travis-ci.org/FitPuli/abele.svg?branch=master)](https://travis-ci.org/FitPuli/abele) [![Download](https://api.bintray.com/packages/fitpuli/fitpuli.dev/abele/images/download.svg)](https://bintray.com/fitpuli/fitpuli.dev/abele/_latestVersion)

Abele is a Kotlin Mutliplatform Logging library with a minimal API.

## Features
-   Easy, extensible API (Timber like)
-   Multithread support in native targets

## Download

**Gradle dependency:**

```gradle
allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/fitpuli/fitpuli.dev' }
    }
}
```

```gradle
dependencies {
    implementation 'hu.fitpuli:abele:0.1.0'
}
``` 

## Usage
Two easy steps:
1.  Install any `Tree` instances you want in the multiplatform module initialization.
2.  Call `Abele`'s static methods everywhere throughout your app.

## Credits
Idea from
-   [Timber](https://github.com/JakeWharton/timber)
-   [Napier](https://github.com/AAkira/Napier)

Big thanks to
-   [Touchlab](https://github.com/touchlab)

## License

    Copyright 2017 FitPuli Kft.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
