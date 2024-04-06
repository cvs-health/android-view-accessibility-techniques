# Tap Target Grouping
Group adjacent tap targets that perform the same action. Such consolidation supports the WAI Mobile Task Force guidance about [Grouping operable elements that perform the same action](https://w3c.github.io/Mobile-A11y-TF-Note/#grouping-operable-elements-that-perform-the-same-action).

The key techniques are:

* Enclose all the adjacent elements that perform the same action in a single layout ViewGroup and apply the following properties:
    * `android:importantForAccessibility="yes"` (for accessibility focusability and to combine all child element text into a single TalkBack announcement)
    * `android:focusable="true"` (for keyboard focusability on older versions of Android)
    * `android:background="?attr/selectableItemBackground"` (to show taps with the proper selection ripple effect)

```
<LinearLayout
    android:id="@+id/linear_layout_example"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:background="?attr/selectableItemBackground"
    android:focusable="true"
    android:importantForAccessibility="yes">

    <ImageButton
        android:id="@+id/image_button_example"
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:background="@android:color/transparent"
        android:clickable="false"
        android:contentDescription="@null"
        android:focusable="false"
        android:importantForAccessibility="no"
        android:src="@drawable/ic_info_circle_outline" />

    <TextView
        android:id="@+id/text_clickable_example"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="App information" />
</LinearLayout>
```
* Disable any redundant content description of elements within the ViewGroup, as is done with the `<ImageButton/>` above.  (Alternatively, override the `contentDescription` of the entire ViewGroup.)

* Apply the `onClickListener` to the enclosing layout, not the enclosed visual tap targets.

```
binding.linearLayoutExample.setOnClickListener {
    Snackbar
        .make(binding.root, "Group tap target clicked", Snackbar.LENGTH_LONG)
        .show()
}
```

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