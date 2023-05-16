Writing Unit Test
======

## `@Runwith`, `@ExtendWith`
You should use `@Runwith` or `@ExtendWith`
- JUnit4: `@RunWith`
- JUnit5: `@ExtendWith`

```kotlin
// build.gradle.kts
val springbootVersion = "2.7.9"
testImplementation("org.springframework.boot:spring-boot-starter-test:${springbootVersion}") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
}

val junitVersion = "5.8.1"
testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
```

```kotlin
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

class MyClassTest {
    // ...
}
```

## Kotest Behavior Spec: given-when-then pattern
with Kotest, you can use given-when-then pattern easiliy.

```kotlin
// build.gradle.kts
val kotestVersion = "5.5.5"
testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
```

```kotlin
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import io.kotest.core.spec.style.BehaviorSpec

class MyTestCase : BehaviorSpec ({
    Given("Given status/condition/environment that ...") {
        When("If input/status/operation ...") {
            Then("Assert that ...") {
                // ...
            }
        }
        
    }
})
```