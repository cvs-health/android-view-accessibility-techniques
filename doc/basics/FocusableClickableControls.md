# Focusable, Clickable Controls
If an active Android control is touchable, it must set `android:focusable="true"` and apply an `onClickListener`, not just an `onTouchListener`. These two steps are required to support the WCAG 2 requirements [Success Criterion 2.1.1 Keyboard](https://www.w3.org/TR/WCAG21/#keyboard) and [Success Criterion 2.5.2 Pointer Cancellation](https://www.w3.org/TR/WCAG21/#pointer-cancellation).

Some controls, such as `Button` and `ImageButton` are focusable by default, so do not require adding `android:focusable="true"`; however, any typically non-focusable element, such as `ImageView` needs to be made focusable or a keyboard and Switch Access may be unable to focus on it.

(For some recent versions of Android, only applying the `onClickListener` is necessary, but for backward-compatibility, set `android:focusable="true"` as well.)

```
<ImageView
    android:id="@+id/show_hello_world"
    ...
    android:focusable="true"
    android:contentDescription="Show 'Hello world!' snackbar" />
```
```
binding.showHelloWorld.setOnClickListener {
    Snackbar.make(binding.root, "Hello world!", Snackbar.LENGTH_LONG).show()
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