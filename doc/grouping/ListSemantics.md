# List Semantics
Elements which create a visually-presented list must be marked as a list using the Android Accessibility API. This supports the WCAG [Success Criterion 1.3.1 Info and Relationships](https://www.w3.org/TR/WCAG22/#info-and-relationships), which requires information conveyed through presentation (such as lists) to be programmatically available to accessibility services.

Lists created with the standard list controls, such as `ListView` and `RecyclerView`, have list semantics applied automatically.

But if layout elements are used to manually create a visual list, then `AccessibilityDelegate`s must be used to apply list semantics to those elements manually.

The following extension functions make manually applying list semantics straightforward.

```kotlin
/**
 * Add accessibility collection semantics to a LinearLayout. 
 * Used for manually marking visually-presented lists with list semantics.
 */
fun LinearLayout.addListSemantics(size: Int) {
    accessibilityDelegate = object : View.AccessibilityDelegate() {
        override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                info.collectionInfo = AccessibilityNodeInfo.CollectionInfo(size, 1, false)
            } else {
                @Suppress("DEPRECATION")
                info.collectionInfo = AccessibilityNodeInfo.CollectionInfo.obtain(size, 1, false)
            }
        }
    }
}

/**
 * Add accessibility collection item semantics for a layout's child
 * view. Used for manually marking items in visually-presented 
 * numbered lists.
 *
 * Multiple associated views can share the same index and will be
 * treated semantically as the same list item.
 *
 * Note: index is zero-based.
 */
fun View.addListItemSemantics(index: Int) {
    accessibilityDelegate = object : View.AccessibilityDelegate() {
        override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                info.collectionItemInfo = AccessibilityNodeInfo.CollectionItemInfo(index, 1, 0, 1, false, false)
            } else {
                @Suppress("DEPRECATION")
                info.collectionItemInfo = AccessibilityNodeInfo.CollectionItemInfo.obtain(index, 1, 0, 1, false, false)
            }
        }
    }
}
```

Given the following unnumbered list layout, list semantics can be applied in code as below.

```xml
<LinearLayout
    android:id="@+id/unnumbered_list_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:importantForAccessibility="yes">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="• Set AccessibilityNodeInfo.CollectionInfo on the outer layout with the list's size." />

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="• Set AccessibilityNodeInfo.CollectionItemInfo on numbered list items with each item's 0-based index in the list." />
</LinearLayout>

```


```kotlin
binding.unnumberedListLayout.addListSemantics(size = 2)
```

TalkBack will then announce "In list" when entering the list and "Out of list" when exiting the list.

Given the following numbered list layout, list semantics can be applied in code as below.

```xml
<LinearLayout
    android:id="@+id/numbered_list_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:importantForAccessibility="yes">

    <TextView
        android:id="@+id/text_point_1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="1. Set AccessibilityNodeInfo.CollectionInfo on the outer layout with the list's size." />

    <TextView
        android:id="@+id/text_point_2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="2. Set AccessibilityNodeInfo.CollectionItemInfo on numbered list items with each item's 0-based index in the list." />
</LinearLayout>

```

```kotlin
binding.numberedListLayout.addListSemantics(size = 2)
binding.textPoint1.addListItemSemantics(index = 0)
binding.textPoint2.addListItemSemantics(index = 1)
```

Notes: 

* The visual presentation of these examples could be improved by using `BulletSpan` and `NumberSpan`, respectively. However, neither of those Span classes will announce their bullet or number in TalkBack; the `contentDescription` of those `TextView` elements should be overridden to do so.

* One downside of the automatic list semantics applied to `RecyclerView` is that not all uses of `RecyclerView` create visually-presented lists; it is also used for non-list dynamic content and server-driven user interfaces. In those cases, list semantics is inappropriate. While automatic list semantics can be turned off by adding `android:importantForAccessibility="no"` to the `<RecyclerView>` element, doing so may also have the side-effect of also disabling automatic scrolling in TalkBack. Always test fixes of this nature with actual TalkBack users and make a judgement call about this applicability to your use case.

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
