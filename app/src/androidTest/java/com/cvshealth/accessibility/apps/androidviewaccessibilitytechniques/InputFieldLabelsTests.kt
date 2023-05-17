/*
   Copyright 2023 CVS Health and/or one of its affiliates

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResult.AccessibilityCheckResultType
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResultUtils.matchesCheckNames
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResultUtils.matchesViews
import org.hamcrest.Matchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing) and
 * [Accessibility testing - Automated testing](https://developer.android.com/guide/topics/ui/accessibility/testing#automated).
 */
@RunWith(AndroidJUnit4::class)
class InputFieldLabelsTests {
    // Even though AccessibilityChecks is enabled in the custom test runner, another test validator
    // can be set up in any test class with specifically customized behavior. Here it disables
    // checking on views that are expected to fail.
    init {
        AccessibilityChecks
            .enable()
            .setRunChecksFromRootView(true)
            .setThrowExceptionFor(AccessibilityCheckResultType.INFO)
            .setSuppressingResultMatcher(
                anyOf(
                    // EditText without associated field label deliberately breaks SpeakableTextPresentCheck
                    matchesViews(withId(R.id.edittext_unassociated)),
                    // Use of android:labelFor can cause violations of DuplicateSpeakableTextCheck,
                    // which should be suppressed, either as follows or by decreasing the sensitivity
                    // of tests with setThrowExceptionFor(AccessibilityCheckResultType.ERROR).
                    allOf(
                        matchesCheckNames(`is`("DuplicateSpeakableTextCheck")),
                        matchesViews(withId(R.id.edittext_associated))
                    )
                )
            )
    }

    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun navigateToTestScreen() {
        // Expand the Accessibility Basics section
        onView(withId(R.id.linear_layout_section_1_expand_collapse_header))
            .perform(ViewActions.scrollTo())
            .perform(ViewActions.click())
        // Open the Input field labels demo
        onView(withId(R.id.text_input_field_labels))
            .perform(ViewActions.scrollTo())
            .check(matches(withText(R.string.menu_input_field_labels)))
            .perform(ViewActions.click())
    }

    @Test
    fun checkUnassociateEditText() {
        onView(withId(R.id.edittext_unassociated))
            .perform(ViewActions.scrollTo())
            .perform(ViewActions.typeText("test"))
    }

    @Test
    fun checkUnassociatedCheckbox() {
        onView(withId(R.id.checkbox_unassociated))
            .perform(ViewActions.scrollTo())
            .check(matches(isNotChecked()))
            .perform(ViewActions.click())
            .check(matches(isChecked()))
    }
}