# Custom Accessibility Actions
In addition to customizing the labels associated with pre-existing accessibility actions (such as `ACTION_CLICK`), it is possible to create custom accessibility actions to improve the user experience of those applying Assistive Technologies such as TalkBack and Switch Access.

Custom accessibility actions are commonly used to capture multiple click actions associated with a single logical unit of text, such as a card or list item so that an assistive technology user can both access those actions and navigate the logical units of text more easily.

Consider a card with multiple `TextView` and `Button` controls. All of the units of text can be combined as one with the techniques of [Content grouping](../grouping/ContentGrouping.md), but each button remains a separate focus target that must be swiped across to get to the next card. Adding custom accessibility actions for the button controls resolves this problem: each button on the card becomes a custom accessibility action that is available from the TalkBack menu's "Actions" menu or the Switch Access "Menu" view and are no longer separate focus targets in Accessibility Services.

To implement custom accessibility actions, four steps are required:

* Mark clickable elements within the card or list item layout as `android:importantForAccessibility="no"`. 
This setting prevents those elements from appearing 
individually in Accessibility Services while remaining touch clickable and keyboard focusable.

```
<com.google.android.material.card.MaterialCardView
    android:id="@+id/card"
    ... />
    
    ...
    <ImageButton
        android:id="@+id/like_post"
        ...
        android:src="@drawable/ic_plus_fill"
        android:importantForAccessibility="no" />
    ...
</com.google.android.material.card.MaterialCardView>
```

* Declare ids for the custom accessibility actions in `res/values/ids.xml`.

```
<resources>
    <item name="show_details" type="id"/>
    <item name="like_post" type="id"/>
</resources>
```

* In code, add an `AccessibilityDelegate` to each card (or list item) that adds custom accessibility actions with `AccessibilityNodeInfo.addAction()` and a new instance of `AccessibilityNodeInfo.AccessibilityAction` for each custom action.

```
card.accessibilityDelegate = object : View.AccessibilityDelegate() {
    override fun onInitializeAccessibilityNodeInfo(
        host: View,
        info: AccessibilityNodeInfo
    ) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        info.addAction(
            AccessibilityNodeInfo.AccessibilityAction(
                R.id.show_details, 
                "Show post details"
            )
        )
        info.addAction(
            AccessibilityNodeInfo.AccessibilityAction(
                R.id.like_post, 
                "Like this post"
            )
        )
    }
    ...
}
```

* Also in the `AccessibilityDelegate`, override `fun performAccessibilityAction()` and handle when the custom actions are selected.

```
...
    override fun performAccessibilityAction(
        host: View,
        action: Int,
        args: Bundle?
    ): Boolean = when (action) {
        R.id.show_details -> {
            viewModel.showPostDetails(cardId)
            true
        }
        R.id.like_post -> {
            viewModel.likePost(cardId)
            true
        }
        else -> super.performAccessibilityAction(host, action, args)
    }
...
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