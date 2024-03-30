# Input Field Labels
All input fields must have associated text labels, as required by WCAG 2 [Success Criterion 1.3.1 Info and Relationships](https://www.w3.org/TR/WCAG21/#info-and-relationships). Android uses several techniques to associate fields and labels, depending on the control type.

## Use the `android:labelFor` property to associate `TextView` labels with controls

The most common and generic technique for associating labels with input field controls is to add the `android:labelFor` property to a TextView, indicating what control field is being labeled by that text.

```
<TextView
    ...
    android:labelFor="@+id/name"
    android:text="Name" />
<EditText
    android:id="@id/name"
    ... />
```

## Use the `android:hint` property for `TextInputLayout` controls

The `TextInputLayout`/`TextInputEditText` combined input field control associates the label with the text input field using the `android:hint` property.

```
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/name_layout"
    ...
    android:hint="Name" >
    <com.google.android.material.textfield.TextInputEditText
        ... />
</com.google.android.material.textfield.TextInputLayout>
```

## Use the `android:text` property for self-labeling controls

The `CheckBox`, `SwitchCompat`/`SwitchMaterial`, `RadioButton`, and `Button` controls all use the `android:text` property to display a label associated with the control.

Note that `RadioButton` controls must always be enclosed in a `RadioGroup` control for proper operation and accessibility.

```
<CheckBox
    android:id="@+id/labels_checkbox"
    ...
    android:text="Show labels" />
```

```
<androidx.appcompat.widget.SwitchCompat
    android:id="@+id/labels_switch"
    ...
    android:text="Show labels" />
```

```
<RadioGroup
    ...>
    
    <RadioButton
        android:id="@+id/radiobutton_on"
        ...
        android:text="Labels On" />
    <RadioButton
        android:id="@+id/radiobutton_off"
        ...
        android:text="Labels Off" />
</RadioGroup>
        
```

```
<Button
    android:id="@+id/button_associated"
    ...
    android:text="Submit" />
```

## Labeling RadioButtons with group labels

In addition to applying a field label to each `RadioButton` using `android:text`, it is important to associate the radio button group label to each individual `RadioButton` in order to supply its selection context.

This is done by manipulating each `RadioButton`'s `AccessibilityNodeInfo` object with the `setLabeledBy()` method. Doing that can be simplified with the following extension method:

```kotlin
fun RadioButton.setRadioGroupHeading(radioGroupLabelView: View) {
    ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            
            // Technique: Use setLabeledBy() to associate a group label View with a RadioButton.
            // This is done in addition to setting the RadioButton's field label with android:text.
            info.setLabeledBy(radioGroupLabelView)
        }
    })
}
```

This extension function associates a group label's `View` with `RadioButton`s as follows:

```kotlin
binding.radiobutton1.setRadioGroupHeading(binding.radioGroupLabel)
binding.radiobutton2.setRadioGroupHeading(binding.radioGroupLabel)
```

## Labeling `Slider` and `RangeSlider` controls

Labeling `Slider` and `RangeSlider` controls is more complex and has known issues. What _must_ be done to label a `Slider` for accessibility is to provide a `contentDescription` for the control; otherwise, a `Slider` will not be properly focused or announced by the TalkBack screen reader. 

However, providing a `contentDescription` does _not_ provide a descriptive visual label for the `Slider`, which is required in addition to the programmatically-associated label text by WCAG 2 [Success Criterion 2.4.6 Headings and Labels](https://www.w3.org/TR/WCAG21/#headings-and-labels). Such a visual label can be created using a separate `TextView`, and should probably be associated with the `Slider` using the `android:labelFor` property. 

(That said, since the `contentDescription` is required and may duplicate the visual label text, using `labelFor` can produce a problematic user experience. Be sure to test your resulting user interface using a screen reader. See [[Slider\] ContentDescription causes the talkback to not read correctly #2348](https://github.com/material-components/material-components-android/issues/2348) and [[Slider\] LabelFor does not work #2347](https://github.com/material-components/material-components-android/issues/2347) for more details about the known issues with `Slider` control labeling.)

Note when testing that Slider controls have two parts: the outer slider itself and one or two "thumb" controls. The outer slider will announce itself in TalkBack with something like "Quality rating, Slider. Double-tap to activate." Ignore the "Double-tap to activate" action on the outer slider control -- it doesn't do anything -- instead swipe right (Next) in TalkBack to get to the thumb control, which has the slider's value and adjustment actions, such as '"Swipe up" or "Swipe down" to adjust.'

```
<!-- One technique is associating the visual label TextView with the
     Slider control using android:labelFor. -->
<TextView
    ...
    android:text="Rating"
    android:labelFor="@+id/rating_slider" />
    
<!-- The other, essential technique is setting 
     android:contentDescription on the Slider control. -->
<com.google.android.material.slider.Slider
    android:id="@+id/rating_slider"
    ...
    android:value="5.0"
    android:valueFrom="0.0"
    android:valueTo="10.0"
    android:stepSize="1.0"
    android:contentDescription="Rating" />
```

```
<!-- One technique is associating the visual label TextView with
    the RangeSlider control using android:labelFor. -->
<TextView
    ...
    android:text="Price range"
    android:labelFor="@+id/price_range_slider" />

<!-- The other, essential technique is setting
     android:contentDescription on the RangeSlider control. -->
<com.google.android.material.slider.RangeSlider
    android:id="@+id/price_range_slider_associated"
    ...
    android:valueFrom="0.0"
    android:valueTo="100.0"
    android:stepSize="5.0"
    android:contentDescription="Price Range" />
```

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