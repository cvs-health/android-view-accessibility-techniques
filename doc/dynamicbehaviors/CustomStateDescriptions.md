# Custom State Descriptions
Some standard Android controls also contain _state_ which they expose to Accessibility Services. For example, a `CheckBox` control has two states announced by TalkBack as "Checked" and "Not checked". Similarly, the `SwitchCompat` and `SwitchMaterial` controls have the states "ON" and "OFF". It is possible to customize these state descriptions to improve the user experience in TalkBack.

To change the state description of a stateful `View` control, use `ViewCompat.setStateDescription()` as follows: 

```kotlin
ViewCompat.setStateDescription(
    binding.alarmCheckbox,
    if (binding.alarmCheckbox.checkedState == MaterialCheckBox.STATE_CHECKED)
        "Activated" 
    else 
        "Deactivated"
)
```

This `CheckBox` will announce its state in TalkBack as "Activated" or "Deactivated" instead of "Checked" and "Not checked".

It is also often appropriate to customize the standard click action description to match the state being changed to, instead of the standard "Double-tap to toggle" announcement.

```kotlin
binding.alarmCheckbox.accessibilityDelegate = object : View.AccessibilityDelegate() {
    override fun onInitializeAccessibilityNodeInfo(
        host: View,
        info: AccessibilityNodeInfo
    ) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info.addAction(
            AccessibilityAction(
                AccessibilityAction.ACTION_CLICK.getId(),
            if (binding.alarmCheckbox.checkedState == MaterialCheckBox.STATE_CHECKED)
                "Deactivate alarm" 
            else 
                "Activate alarm"
            )
        )
    }
}
```

This `CheckBox` will now announce its standard click action as "Double-tap to activate alarm" if unchecked and "Double-tap to deactivate alarm" if checked.

Although `ViewCompat.setStateDescription()` does not work successfully on all Android platforms, when it does not, the standard state description text is announced. So attempting to improve the user experience by customizing the state description does no harm.

(Note: The hard-coded text shown in these examples is only used for simplicity. _Always_ use externalized string resource references in actual code.)

----

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
