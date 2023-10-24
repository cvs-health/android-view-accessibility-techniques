# Custom Focus Indicators
By default, all standard Android control types have focus indicators which display when keyboard focus is on a control. However, the default focus indicators are generally low-contrast, which makes the focussed control very hard to identify for keyboard users with low vision. Applying custom focus indicators is one way to better support those users in accordance with the WCAG 2 [Success Criterion 2.4.7 Focus Visible](https://www.w3.org/TR/WCAG21/#focus-visible) and the WCAG 2.2 [Success Criterion 2.4.13 Focus Appearance](https://www.w3.org/TR/WCAG22/#focus-appearance) (Level AAA).

(Note that using the default focus indicator is in technical conformance with WCAG 2.2 [Success Criterion 2.4.13 Focus Appearance](https://www.w3.org/TR/WCAG22/#focus-appearance) (Level AAA), but it provides a poor user experience for keyboard users.)

There are several techniques available to customize the focus indicators for controls; although, some techniques work better for particular controls than others.

## Set the `android:colorControlHighlight` theme property

Setting `android:colorControlHighlight` in the default theme to a high-contrast color can produce more visible focus indicators.

## Apply a color state list with `app:strokeColor`

For some controls, setting `app:strokeWidth` greater to `2dp` (or more), and setting `app:strokeColor` to a color state list can produce a more visible focus indicator. This approach does not work well for all controls, but is effective with Material Design buttons.

```
<Button
    android:id="@+id/button_example"
    ...
    android:text="Submit"
    app:strokeWidth="2dp"
    app:strokeColor="@color/focus_indicator_color_state_list" />
```

The color state list `res/color/focus_indicator_color_state_list.xml` is:

```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_focused="true" android:color="@color/focus_indicator_outline" />
    <item android:color="@android:color/transparent" />
</selector>
```

The color `@color/focus_indicator_outline` also needs to be defined appropriately for both light and dark themes. For example, as `<color name="focus_indicator_outline">#FF000000</color>` in `res/values/colors.xml` and `<color name="focus_indicator_outline">#FFDDDD00</color>` in `res/values-night/colors.xml`.

## Apply a drawable state list with `android:background`

Setting `android:background` to a drawable state list resource that in turn points to an outline drawable can produce a more visible focus indicator.

```
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/text_input_layout_example"
    ...
    android:hint="Name"
    android:background="@drawable/focus_indicator_selector">

    <com.google.android.material.textfield.TextInputEditText
        ...
        android:inputType="text" />
</com.google.android.material.textfield.TextInputLayout>
```

The drawable state list `res/drawable/focus_indicator_selector.xml` is:

```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_focused="true" android:drawable="@drawable/focus_indicator_shape" />
    <item android:drawable="@android:color/transparent" />
</selector>
```

And the outline drawable `res/drawable/focus_indicator_shape.xml` is:

```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item>
        <shape>
            <stroke android:width="2dp" android:color="@color/focus_indicator_outline"/>
            <corners android:radius="4dp"/>
            <padding android:bottom="2dp" android:left="2dp" android:right="2dp" android:top="2dp" />
        </shape>
    </item>
</selector>
```

Again, the color `@color/focus_indicator_outline` needs to be defined appropriately for both light and dark themes (as above). 

## Apply a drawable state list with `android:foreground` (API 23+)

Starting with Android API level 23, the property `android:foreground` is available and can be assigned a drawable state list for controls which already provide a defined background (such as `ImageButton` with its ripple background on selection). This technique also works with `SwitchMaterial`, `MaterialCheckBox`, `EditText`, and `TextInputLayout`/`TextInputEditText`, but not with `MaterialButton`.

```
<ImageButton
    ...
    android:foreground="@drawable/focus_indicator_selector" />
```

The `drawable/focus_indicator_selector.xml` used above (and its dependencies) would also apply in this case.

If API levels before 23 must be supported, then a similar effect may be achieved using `android:background` and a drawable layer list to combine  the default selectable item background with the custom focus indicator drawable. (Not illustrated here.)

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