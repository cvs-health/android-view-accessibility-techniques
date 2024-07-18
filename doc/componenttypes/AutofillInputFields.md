# Autofill Input Fields
Whenever possible, supply known data values to input fields to reduce redundant data entry. This is done in support of WCAG [Success Criterion 1.3.5 Identify Input Purpose](https://www.w3.org/TR/WCAG22/#identify-input-purpose) and [Success Criterion 3.3.7 Redundant Entry](https://www.w3.org/TR/WCAG22/#redundant-entry).

Two approaches to auto-filling input field values are to provide hints to an Autofill Service and to supply known application data.

## Provide hints to an Autofill Service

Text input fields can be autofilled by supplying the android:autofillHints property, so that any installed Autofill Service can provide suggested data values. See [Optimize your app for autofill](https://developer.android.com/guide/topics/text/autofill-optimize) for details.

* This approach, and supplying `android:inputType`, are necessary to fulfill WCAG [Success Criterion 1.3.5 Identify Input Purpose](https://www.w3.org/TR/WCAG22/#identify-input-purpose) on Android.

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

## Supply known application data

Input fields can also be auto-populated by supplying default data values from known application data. For example, shipping address fields can be filled from previously-entered billing address data, which will simplify data entry when the billing and shipping addresses are the same.

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
