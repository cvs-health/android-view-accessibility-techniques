# Keyboard Types
It is generally necessary to explicitly set the soft keyboard type of text input controls in order to obtain the appropriate values for data, create a good user experience, and to support part of the WCAG [Success Criterion 1.3.5 Identify Input Purpose](https://www.w3.org/TR/WCAG22/#identify-input-purpose).

The primary technique for setting the soft keyboard type is applying the correct `android:inputType` value to the text input field. This list of `inputType` values is available in the Android documentation:
[Specify the input method type](https://developer.android.com/develop/ui/views/touch-and-input/keyboard-input/style) and [android:inputType](https://developer.android.com/reference/android/widget/TextView#attr_android:inputType).

In the following example, a text input field accepts a phone number using the dial keypad style of soft keyboard using `android:inputType="phone"`.

```xml
<com.google.android.material.textfield.TextInputLayout
    ...
    android:hint="Phone" >
    
    <com.google.android.material.textfield.TextInputEditText
        ...
        android:autofillHints="phoneNumber"
        android:inputType="phone" />
        
</com.google.android.material.textfield.TextInputLayout>
```

(The other part of supporting WCAG [Success Criterion 1.3.5 Identify Input Purpose](https://www.w3.org/TR/WCAG22/#identify-input-purpose) is supplying `android:autofillHints`, as shown above. See [Autofill Input Fields](../componenttypes/AutofillInputFields.md) for more details.)

(Note: The hard-coded text shown in these examples is only used for simplicity. _Always_ use externalized string resource references in actual code.)

----

Copyright 2023-2024 CVS Health and/or one of its affiliates
   
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
       
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   
See the License for the specific language governing permissions and
limitations under the License.
