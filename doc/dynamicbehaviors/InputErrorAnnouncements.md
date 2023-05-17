# Input Error Announcements
All errors resulting from user interactions must display visible notifications with suggestions for correction and must be announced to accessibility services (such as TalkBack). These actions support WCAG 2 [Success Criterion 3.3.1 Error Identification](https://www.w3.org/TR/WCAG21/#error-identification), [Success Criterion 3.3.3 Error Suggestion](https://www.w3.org/TR/WCAG21/#error-suggestion), and [Success Criterion 4.1.3 Status Messages](https://www.w3.org/TR/WCAG21/#status-messages).

One set of techniques that can help provide input error announcements involves the `TextInputLayout`/`TextInputEditText` combined controls:

* Set `app:errorEnabled="true"` on the control in layout.
* On error, set the `TextInputLayout.error` property to an informative error message.
* Move accessibility focus to the first field in error using `performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null)` and `sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)`

* When the field value is valid, set the `TextInputLayout.error` property to the empty string to clear any prior error messages.

```
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/textinputlayout_name"
    ...
    android:hint="Full Name"
    app:errorEnabled="true" >

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/textinputedittext_name"
        ...
        android:inputType="text"
        android:maxLines="1" />
</com.google.android.material.textfield.TextInputLayout>
```

```
if (validationResults.isNameInError) {
    binding.textinputlayoutName.apply {
        // Put accessibility focus on the name field if it is in error.        
        // Keyboard focus remains on the Submit button.
        editText?.text?.clear()
        editText?.performAccessibilityAction(
            AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS,
            null
        )
        editText?.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        
        // Delay the error announcement, which is polite, 
        // until after the focus change.
        post {
            error = "Error: The Full Name field is required. Please supply your full name."
        }
    }
} else {
    // If the field value is valid, clear any prior error state.
    binding.textinputlayoutName.error = ""
}
```

Many other approaches to input error messaging and announcement are possible.


(Note: The hard-coded text shown in these examples is only used for simplicity. _Always_ use externalized string resource references in actual code.)

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