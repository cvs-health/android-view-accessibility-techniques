# UX Change Announcements
All user experiences changes resulting from user interactions or automatic updates must be announced to accessibility services (such as TalkBack) in order to support the WCAG 2 [Success Criterion 4.1.3 Status Messages](https://www.w3.org/TR/WCAG21/#status-messages). (That said, it is reasonable and acceptable to rate-limit the announcement of automatic updates or they can overwhelm the user.)

There are two key techniques for providing UX change announcements: `accessibilityLiveRegion` and `announceForAccessibility()`.

## Use the `android:accessibilityLiveRegion` property to announce field value changes

The `android:accessibilityLiveRegion` property indicates a field that should announce its value whenever it is changed. This property has three possible values: `"polite"` waits for any announcement in progress to complete, `"assertive"` interrupts any announcement in progress, and `"none"` indicates that the field should not announce its value.

Note that fields with `accessibilityLiveRegion` active will announce their value when they are made visible, but no announcement is made when they are made not visible. (See the next technique for a way to handle that.)

In the following example, whenever the `counter` field's text is updated, its new value will be announced to accessibility services.

```
<TextView
    android:id="@+id/counter"
    ...
    android:accessibilityLiveRegion="polite"
    tools:text="Counter: 0"/>
```

## Use the `View.announceForAccessibility()` method to announce events from code

The `View.announceForAccessibility()` method can be called at any point from code to make an announcement. This announcement is always "assertive."

The following example shows code announcing when a waiting indicator is dismissed using `announceForAccessibility()`. 

```
binding.waitingIndicator.announceForAccessibility(
    "Loading completed."
)
binding.waitingIndicator.hide()
```

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