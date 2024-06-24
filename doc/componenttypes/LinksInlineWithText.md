# Links Inline with Text
Always create links which are inline with text that announce their name, role, and value in accordance with the WCAG [Success Criterion 4.1.2 Name, Role, Value](https://www.w3.org/TR/WCAG22/#name-role-value). The goal is to make TalkBack announce a `TextView` with the "Links available..." action message and display the TalkBack menu's Links menu.

Two approaches to creating links inline with text are adding HTML anchor tags in string resources and URLSpans.

## HTML anchor tags in string resources

Embed an HTML anchor tag in a string resource displayed by a `TextView` to create a link inline with the text. Adding an HTML `<a>` tag to a string resource used by a `TextView` is straightforward, but making a workable link also requires setting the `TextView.movementMethod` in code.

```xml
    <string name="anchor_tag_string">This string contains an HTML anchor tag link: <a href="https://www.google.com/search?q=android+link+in+textview">Search for \"android link in textview\"</a>.</string>
```

```xml
<com.google.android.material.textview.MaterialTextView
    android:id="@+id/anchor_tag_text_view"
    ...
    android:text="@string/anchor_tag_string" />
```

```kotlin
binding.anchorTagTextView.movementMethod = LinkMovementMethod.getInstance()
```

## URLSpans

Use an URLSpan in code to impose one or more links on segments of text in a `SpannableString`, then assign that text to a `TextView`, and set the `TextView.linkMovementMethod` as before.

```kotlin
val text = SpannableString("Text containing an URLSpan.")
val start = text.indexOf("URLSpan")
val end = start + "URLSpan".length
text.setSpan(
    URLSpan("https://developer.android.com/reference/android/text/style/URLSpan"), 
    start, 
    end, 
    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
)
binding.urlSpanTextView.text = text
binding.urlSpanTextView.movementMethod = LinkMovementMethod.getInstance()
```

(Note: The hard-coded text shown in these examples are only used for simplicity. _Always_ use externalized string resource references in actual code.)

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
