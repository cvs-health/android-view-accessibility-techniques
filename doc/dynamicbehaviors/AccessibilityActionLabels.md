# Accessibility Action Labels
Standard accessibility actions, such as activating a control using an Assistive Technology like TalkBack by double-tapping, can have their label text customized to improve the user experience.

In this way, a button that might normally announce its action as "Double-tap to activate" can instead be programmed to say "Double-tap to show details" or "Double-tap to sign in". 

(Note how assigning a custom action label does not change TalkBack's standard click action description "Double-tap to ...", only what is said after that point.)

A customized action label is added to a button by assigning an `AccessibilityDelegate` that applies `addAction()` to the preexisting standard click action's id (`AccessibilityAction.ACTION_CLICK.getId()`) with the custom label text (in this case, `"show details"`):

```
binding.showDetailsButton.accessibilityDelegate = object : View.AccessibilityDelegate() {
    override fun onInitializeAccessibilityNodeInfo(
        host: View,
        info: AccessibilityNodeInfo
    ) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info.addAction(
            AccessibilityAction(
                AccessibilityAction.ACTION_CLICK.getId(),
                "show details"
            )
        )
    }
}
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