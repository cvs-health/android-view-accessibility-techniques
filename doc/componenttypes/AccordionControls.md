# Accordion Controls
Accordion controls cause sections of a screen to expand, revealing additional content, or to collapse, concealing that content. To make accordion controls conform to the WCAG [Success Criterion 4.1.2 Name, Role, Value](https://www.w3.org/TR/WCAG22/#name-role-value), it is necessary for such controls to support the Android standard accessibility actions `ACTION_EXPAND` (if collapsed) and `ACTION_COLLAPSE` (if expanded). Only by declaring those accessibility actions is the expanded or collapsed state of an accordion control surfaced to the Android Accessibility API.

The technique for applying these standard actions is to create an `AccessibilityDelegate` for the accordion control. 

Override `fun onInitializeAccessibilityNodeInfo()` so that, if the accordion is expanded, `AccessibilityNodeInfo.removeAction()` is called to remove `ACTION_EXPAND` and `AccessibilityNodeInfo.addAction()` is called to add `ACTION_COLLAPSE`. If the section is collapsed, `ACTION_COLLAPSE` is removed and `ACTION_EXPAND` is added.

Also, an override of `fun performAccessibilityAction()` must be present in the `AccessibilityDelegate` in order to handle the expand and collapse actions.


```kotlin
sectionHeader.accessibilityDelegate = object : View.AccessibilityDelegate() {
    override fun onInitializeAccessibilityNodeInfo(
        host: View,
        info: AccessibilityNodeInfo
    ) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        if (sectionExpanded) {
            info.removeAction(
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_EXPAND,
                    null
                )
            )
            info.addAction(
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_COLLAPSE,
                    null
                )
            )
        } else {
            info.removeAction(
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_COLLAPSE,
                    null
                )
            )
            info.addAction(
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_EXPAND,
                    null
                )
            )
        }
    }

    override fun performAccessibilityAction(
        host: View,
        action: Int,
        args: Bundle?
    ): Boolean = when (action) {
        AccessibilityNodeInfo.ACTION_EXPAND,
        AccessibilityNodeInfo.ACTION_COLLAPSE -> {
            viewModel.toggleAccordion(sectionId)
            true
        }
        else -> super.performAccessibilityAction(host, action, args)
    }
}
```

The accordion control must also be a focusable, clickable control in order to support keyboard operation. See [Focusable, Clickable Controls](../basics/FocusableClickableControls.md) for details.

```kotlin
binding.accordion.setOnClickListener { 
    viewModel.toggleAccordion(sectionId) 
}
```

Accordion controls must actually show or hide their content, as well as visually indicate their expanded or collapsed state. Expanded or collapsed state is often indicated with icons. For example:

```kotlin
if (pageModel.accordionExpanded) {
    binding.accordionContent.visibility = View.VISIBLE
    binding.accordion.setImageResource(R.drawable.ic_up_caret)
} else {
    binding.accordionContent.visibility = View.GONE
     binding.accordion.setImageResource(R.drawable.ic_down_caret)
}
```


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
