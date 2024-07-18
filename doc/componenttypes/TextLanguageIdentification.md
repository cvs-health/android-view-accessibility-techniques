# Text Language Identification
Texts in different languages will be mispronounced by the text-to-speech engine within the TalkBack screen reader unless their handling is properly coded. Mispronunciation can serious reduce the accessibility of such texts. Using appropriate text language identification techniques supports the WCAG [Success Criterion 3.1.2 Language of Parts](https://www.w3.org/TR/WCAG22/#language-of-parts).

The main technique to support proper text identification on a mixed-language page, and especially in a mixed-language `TextView` text is `LocaleSpan`.

```kotlin
val exampleText = "The phrase \"plus ça change, plus c\'est la même chose\" translates as \"the more things change, the more they stay the same.\""
val wrappedExampleText = SpannableString(exampleText)
val firstQuotationMarkLocation = exampleText.indexOf('"')
val secondQuotationMarkLocation = exampleText.indexOf('"', firstQuotationMarkLocation + 1)
wrappedExampleText.setSpan(
    LocaleSpan(Locale.FRENCH),
    firstQuotationMarkLocation,
    secondQuotationMarkLocation + 1,
    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
)
binding.mixedLanguageText.text = wrappedExampleText
```

These techniques can also be wrapped into a custom `TextView` subclasses, which can be very helpful for entire texts that are in specific languages or to encapsulate the parsing of more complex language-identification markup within texts.

Also be sure to incorporate Google's guidance about [per-app language preferences](https://developer.android.com/guide/topics/resources/app-languages) in support of the WCAG [Success Criterion 3.1.1 Language of Page](https://www.w3.org/TR/WCAG22/#language-of-page). These techniques are available for Android 13+ or with the appcompat library version 1.6+.

(Note: The hard-coded text and language locale shown in these examples are only used for simplicity. _Always_ use externalized string resource references in actual code.)

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
