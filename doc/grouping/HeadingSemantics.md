# Heading Semantics
Top-level heading text must be marked as an accessibility heading. This supports the WCAG 2 [Success Criterion 1.3.1 Info and Relationships](https://www.w3.org/TR/WCAG21/#info-and-relationships), which requires information conveyed through presentation (such as larger-sized heading text) to be programmatically available to accessibility services.

Two techniques can be used to mark content as a heading for accessibility.

* For API level 28 (Android 9) and later, set `android:accessibilityHeading="true"`.
    * Note: This property may be applied to `TextView` or to layout elements that group heading content.
* For support below API level 28, apply an `AccessibilityDelegateCompat` to a `View` using `ViewCompat.setAccessibilityDelegate()` that sets the `AccessibilityNodeInfoCompat.isHeading` property to `true`. 

```
ViewCompat.setAccessibilityDelegate(
    binding.heading, 
    object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            info.isHeading = true
        }
    }
)
```

The following extension function simplifies applying the heading property to a `View`.

```
fun View.setAsAccessibilityHeading() {
    ViewCompat.setAccessibilityDelegate(
        this, 
        object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.isHeading = true
            }
        }
    )
}
```

Marking an element as an accessibility heading then becomes a simple application of the extension function.

```
binding.heading.setAsAccessibilityHeading()
```

Notes:

* One complication of heading semantics on Android is that list items in the standard list classes (such as `RecyclerView`) can not be accessibility headings; the `isHeading` property is simply ignored for list items. Logically, this makes a certain sense since list `ViewHolder` items are not permanent, but are recycled as the list is scrolled.
* Never use `android:contentDescription` to append an accessibility property like "Heading" to a `View`. It may sound almost correct in TalkBack, but doesn't work correctly.
* Native Android apps do not have multiple heading levels. Only mark top-level headings as a heading.

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