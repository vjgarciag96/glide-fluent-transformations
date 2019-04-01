# glide-fluent-transformations
[![CircleCI](https://circleci.com/gh/vjgarciag96/glide-fluent-transformations/tree/master.svg?style=svg&circle-token=19812333132e2142f6c5623f97ef7f219b31f998)](https://circleci.com/gh/vjgarciag96/glide-fluent-transformations/tree/master)[ ![Download](https://api.bintray.com/packages/vjgarciag96/glide-fluent-transformations/com.vjgarcia.glidefluenttransformations/images/download.svg?version=0.4-beta) ](https://bintray.com/vjgarciag96/glide-fluent-transformations/com.vjgarcia.glidefluenttransformations/0.4-beta/link)

Glide fluent transformation is a library written in Kotlin that allows you to add extra bitmap transformations to your code with a fluent syntax beyond the Glide core transformations

### Set Up
Add the next dependency in the build.gradle of the android module where you want to use this library:

`implementation "com.vjgarcia:glide-fluent-transformations:0.0.4"`

### Usage
#### Kotlin 
Use library transformations as Glide usual transformations

```kotlin
Glide.with(this)
     .load(anyURI)
     .topCrop()
     .into(anyImageView)
```

#### Java 
TBD

### Sample project
You can check an example of the library usage at app module of this project
