Writing Unit Test
======

with Kotest, you can use given-when-then pattern easiliy.

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