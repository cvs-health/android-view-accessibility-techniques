# Dropdown Selection Menus
To create dropdown list controls that announce their name, role, and value in accordance with the WCAG [Success Criterion 4.1.2 Name, Role, Value](https://www.w3.org/TR/WCAG22/#name-role-value), use the standard Android control `Spinner` and the Material Design [Exposed Dropdown Menu pattern](https://m2.material.io/components/menus#exposed-dropdown-menu). Custom approaches are likely to be less accessible; make sure any dropdown selection menu announces the role "Drop down list".

## The `Spinner` control

Use the pre-Material Design control `Spinner` with a `TextView` label to create a dropdown selection menu in the original Android style.

```
<TextView
    ...
    android:text="Payment type"
    android:labelFor="@+id/spinner" />

<Spinner
    android:id="@+id/spinner"
    ...
    android:minHeight="48dp" />
```

## The Exposed Dropdown Menu pattern

Use the [Exposed Dropdown Menu pattern](https://m2.material.io/components/menus#exposed-dropdown-menu) with `TextInputLayout` and `AutoCompleteTextView` to create a dropdown selection menu in the Material Design style.

* Applying `style="@style/Widget.MaterialComponents.TextInputLayout.FilledBox.ExposedDropdownMenu"` is required to achieve the dropdown menu look-and-feel.

* `android:inputType="none"` is used to prevent this `AutoCompleteTextView` from accepting user keyboard text (only data from the dropdown selection list is allowed).

```
<com.google.android.material.textfield.TextInputLayout
    ...
    style="@style/Widget.MaterialComponents.TextInputLayout.FilledBox.ExposedDropdownMenu"
    android:hint="Payment type">

    <AutoCompleteTextView
        ...
        android:inputType="none" />
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