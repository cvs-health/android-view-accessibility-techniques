# Keyboard and Accessibility Focus Order
Sequence all focusable active control elements appropriately so they are activated by the keyboard or assistive technologies (e.g., TalkBack, Switch Access) in an order that preserves the meaning of their visual layout. Incorrect control focus sequences are hard to operate or understand and violate the WCAG 2 [Success Criterion 2.4.3 Focus Order](https://www.w3.org/TR/WCAG21/#focus-order).

Also, avoid creating focus traps, in accordance with WCAG 2 [Success Criterion 2.1.2 No Keyboard Trap](https://www.w3.org/TR/WCAG21/#no-keyboard-trap).

There are three techniques for influencing keyboard and accessibility focus order: correct layout grouping and sequencing, `nextFocusForward`, and `accessibilityTraversalBefore` / `accessibilityTraversalAfter`.

## Correct layout grouping and sequencing

Set the active control focus order by correctly sequencing layout elements and controls into properly ordered groups.

* For example, to create a two-column control layout in which each column is read completely before the other column is read, the outer layout should have `android:orientation="horizontal"` with two inner vertical layouts, one for each column of text. (If instead the outer layout is vertically-oriented, with inner two-element horizontal layouts, the paragraphs of the main column and the sidebar column will be mixed together in focus order.)

* Also, put the controls in focus order sequence within the XML layout.

## Use the `android:nextFocusForward` property to set keyboard focus order

Set the property `android:nextFocusForward` to the `android:id` of the next element in keyboard (but not accessibility) focus order.

* This technique is sometimes required on older versions of Android in addition to correctly sequencing the layout elements and controls.

* Care must be taken never to create a focus loop by pointing `android:nextFocusForward` to an element id that is earlier in the  focus order.

```
<LinearLayout
    ...
    android:orientation="horizontal">
    
    <LinearLayout
        ...
        android:layout_weight="1"
        android:orientation="vertical">

        <TextView
            ...
            android:text="Group 1" />

        <CheckBox
            android:id="@+id/checkbox_1a"
            ...
            android:text="Checkbox 1a"
            android:nextFocusForward="@+id/checkbox_1b"
            />

        <CheckBox
            android:id="@+id/checkbox_1b"
            ...
            android:text="Checkbox 1b"
            android:nextFocusForward="@+id/checkbox_2a"
            />
    </LinearLayout>

    <LinearLayout
        ...
        android:orientation="vertical">

        <TextView
            ...
            android:text="Group 2" />

        <CheckBox
            android:id="@+id/checkbox_2a"
            ...
            android:text="Checkbox 2a"
            android:nextFocusForward="@+id/checkbox_2b"
            />

        <CheckBox
            android:id="@+id/checkbox_2b"
            ...
            android:text="Checkbox 2b"
            />
    </LinearLayout>
</LinearLayout>
```

## Use the `android:accessibilityTraversalBefore` or `android:accessibilityTraversalAfter` properties to set accessibility focus order -- if necessary

Apply the property `android:accessibilityTraversalBefore` or `android:accessibilityTraversalAfter` to control accessibility (but not keyboard) focus order, if and only if it is _essential_ to do so; otherwise, use correct layout sequence and structure to set accessibility focus order. 

These properties do not work on all Android platforms. They create maintenance issues in layout files over time. And they are known to fail if they must scroll content in order to move the accessibility focus. Therefore, using these properties should be avoided if at all possible.

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