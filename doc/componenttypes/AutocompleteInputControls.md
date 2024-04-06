# Autocomplete Input Controls
Autocomplete input controls provide a drop-down list of suggested values, based on the user's present input text. To make autocomplete input controls conform to the WCAG [Success Criterion 4.1.2 Name, Role, Value](https://www.w3.org/TR/WCAG22/#name-role-value), it is best to implement them using the standard `AutoCompleteTextView` control; custom approaches are likely to be less accessible.

Implementation of `AutoCompleteTextView` is discussed in [Provide auto-complete suggestions](https://developer.android.com/develop/ui/views/touch-and-input/keyboard-input/style#AutoComplete) and [AutoCompleteTextView](https://developer.android.com/reference/android/widget/AutoCompleteTextView). Use the property `android:completionThreshold` to control how many characters the user must type before automatic completion options are displayed.

There are two common approaches to incorporating `AutoCompleteTextView` into a layout with a visual label: beside a `TextView` or within a `TextInputLayout`.

## `AutoCompleteTextView` labeled by a `TextView`

If `AutoCompleteTextView` is accompanied by a sibling `TextView` as its visual label, be sure to use `android:labelFor` on the `TextView` indicating the autocomplete control's `android:id`.

```
<TextView
    ...
    android:text="US State"
    android:labelFor="@+id/auto_complete_us_state" />

<AutoCompleteTextView
    android:id="@+id/auto_complete_us_state"
    ...
    android:completionThreshold="1">
```

## `AutoCompleteTextView` labeled by a `TextInputLayout`

If `AutoCompleteTextView` is incorporated into a `TextInputLayout` to provide its visual label, apply `style="@style/Widget.MaterialComponents.TextInputLayout.FilledBox.ExposedDropdownMenu"` to get a "drop-down menu" appearance.

```
<com.google.android.material.textfield.TextInputLayout
    ...
    android:hint="US State"
    style="@style/Widget.MaterialComponents.TextInputLayout.FilledBox.ExposedDropdownMenu" >

    <AutoCompleteTextView
        ...
        android:completionThreshold="1"
        android:inputType="textCapWords" />

</com.google.android.material.textfield.TextInputLayout>
```

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