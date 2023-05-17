# Espresso Automated Accessibility Testing
The same accessibility checks that are run by Android Accessibility Scanner can be incorporated into Espresso automated UI tests.

The following steps will add this functionality to all UI tests:

* In `app/build.gradle`, add the Espresso and Espresso Accessibility library dependencies:

```
dependencies {
    ...
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    androidTestImplementation 'androidx.test.espresso:espresso-accessibility:3.4.0'
}
```

* Create a custom test runner class:

```
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.runner.AndroidJUnitRunner

class AccessibilityChecksTestRunner : AndroidJUnitRunner() {
    init {
        AccessibilityChecks
            .enable()
            .setRunChecksFromRootView(true)
            .setThrowExceptionFor(AccessibilityCheckResultType.ERROR) // Or INFO, WARNING, ... 
            // Use .setSuppressingResultMatcher(...) to omit tests with false positive test results.
    }
}
```

The `.setThrowExceptionFor(...)` method will insure that the appropriate level of accessibility errors fail the UI tests by throwing exceptions. 

* In `app/build.gradle`, declare the custom test runner in `defaultConfig`:

```
android {
    ...
    defaultConfig {
        ...
        testInstrumentationRunner 'com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.AccessibilityChecksTestRunner'
    }
    ...
}
```

* Create Espresso UI tests that exercise all controls that you want checked for accessibility issues. The test must perform a `ViewAction` for accessibility checks to be run.

```
    ...
    @Test
    fun testCheckbox() {
        onView(withId(R.id.checkbox))
            .perform(ViewActions.scrollTo())
            .check(matches(isNotChecked()))
            .perform(ViewActions.click())
            .check(matches(isChecked()))
    }
```

Note that test classes can instantiate their own version of `AccessibilityChecks` with customized behaviors. See `InputFieldLabelTests.kt` for an example.


See [Accessibility testing - Automated testing](https://developer.android.com/guide/topics/ui/accessibility/testing#automated) for more details.


----

Copyright 2023 CVS Health and/or one of its affiliates

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
[http://www.apache.org/licenses/LICENSE-2.0]()

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

See the License for the specific language governing permissions and
limitations under the License.