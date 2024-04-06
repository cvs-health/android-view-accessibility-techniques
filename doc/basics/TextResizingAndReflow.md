# Text Resizing and Reflow
TextView controls in Android should obey the following rules:

1. Use `sp` ("scaled pixel") units for `android:textSize`.
2. Use `layout_height="wrap_content"` (or `layout_height="0dp"` in a `ConstraintLayout`).
3. Do not set `android:maxLines` (or any other `max...` property) -- unless some mechanism exists to make the full text available.

These steps are required to support WCAG [Success Criterion 1.4.4 Resize Text](https://www.w3.org/TR/WCAG22/#resize-text) and [Success Criterion 1.4.10 Reflow](https://www.w3.org/TR/WCAG22/#reflow).

The first rule insures that text will scale when the system text size is adjusted in the Android Settings app "Accessibility > Text and display > Font size" screen (Google devices) or "Accessibility > Visibility enhancements > Font size and style" screen (Samsung devices).

The second rule insures that the `TextView` itself does not cut off the top or bottom of the text displayed. (Similarly, do not put `TextView` controls in a fixed-height layout container.)

The third rule insures that the entire text can display by reflowing vertically if it grows longer than a fixed number of lines -- unless some other mechanism (like a link to the full text or an expandable view) is provided.



```
<TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textSize="16sp"
    android:text="This text will resize and reflow properly."
/>
```

(Note: The hard-coded text shown in this example is only used for simplicity. _Always_ use externalized string resource references in actual code.)

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