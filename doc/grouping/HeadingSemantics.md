# Heading Semantics
The headings of the most important sections of a screen must be marked as semantic accessibility headings. This supports the WCAG [Success Criterion 1.3.1 Info and Relationships](https://www.w3.org/TR/WCAG22/#info-and-relationships), which requires information conveyed through presentation (such as larger-sized heading text) to be programmatically available to accessibility services. However, Android only has one level of heading, unlike the web which has multiple heading levels, so which headings should be marked as semantic headings presents design trade-offs.

Two techniques can be used to mark content as a heading for accessibility.

* For API level 28 (Android 9) and later, set `android:accessibilityHeading="true"`.
    * Note: This property may be applied to `TextView` or to layout elements that group heading content.
* For support below API level 28, apply an `AccessibilityDelegateCompat` to a `View` using `ViewCompat.setAccessibilityDelegate()` that sets the `AccessibilityNodeInfoCompat.isHeading` property to `true`. 

```kotlin
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

```kotlin
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

```kotlin
binding.heading.setAsAccessibilityHeading()
```

Notes:

* One complication of heading semantics on Android is that list items in the standard list classes (such as `RecyclerView`) can not be accessibility headings; the `isHeading` property is simply ignored for list items. Logically, this makes a certain sense since list `ViewHolder` items are not permanent, but are recycled as the list is scrolled.
* Never use `android:contentDescription` to append an accessibility property like "Heading" to a `View`. It may sound almost correct in TalkBack, but doesn't work correctly.
* Native Android apps do not have multiple heading levels, so navigation of complex information hierarchies using headings in TalkBack can be challenging. Consider limiting screen content and only marking top-level section headings as semantic headings.
* It is helpful to make app bar titles semantic headings. However, a `Toolbar` _default_ title `TextView` can only be made a semantic heading using a hack that relies on the internal details of the `Toolbar` class. This approach is demonstrated in [MainActivity.kt](/app/src/main/java/com/cvshealth/accessibility/apps/androidviewaccessibilitytechniques/MainActivity.kt) for reference, but is not recommended.

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
