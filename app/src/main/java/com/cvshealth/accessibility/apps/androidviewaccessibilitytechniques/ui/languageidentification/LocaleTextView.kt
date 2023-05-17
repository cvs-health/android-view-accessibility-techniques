/*
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
 */
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.languageidentification

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.LocaleSpan
import android.util.AttributeSet
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.google.android.material.textview.MaterialTextView
import java.util.*

/**
 * A custom view component for displaying a text that is in a specific language by wrapping the text
 * in an appropriate LocaleSpan. This component simplifies presentation of single-language texts
 * with known language that might differ from the system- or app-configured language. Otherwise,
 * these texts are at risk of being mispronounced by the configured text-to-speech engine in the
 * TalkBack screen reader, reducing accessibility.
 *
 * This component is best used on mixed-language screens containing separate single-language texts,
 * such as a translation app or a language-instruction app.
 *
 * While this component could be used for mono-lingual apps to insure correct text-to-speech
 * rendering on systems configured with different languages, other APIs are more appropriate for
 * this purpose. (See https://developer.android.com/guide/topics/resources/app-languages.)
 */
class LocaleTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): MaterialTextView(context, attrs, defStyleAttr) {
    // For key techniques, see class-level KDoc, function-level KDoc, and in-line comment below.

    var locale: Locale? = null
        set(value) {
            field = value
            updateText()
        }

    var localizedText: CharSequence? = null
        set(value) {
            field = value
            updateText()
        }

    init {
        if (!isInEditMode) {
            attrs?.let { attributeSet ->
                val typedArray = context.obtainStyledAttributes(
                    attributeSet,
                    R.styleable.LocaleTextView, 0, 0
                )

                val languageTag = typedArray.getNonResourceString(
                    R.styleable.LocaleTextView_locale
                )
                locale = languageTag?.let {
                    Locale.forLanguageTag(it)
                }
                localizedText = typedArray.getNonResourceString(
                    R.styleable.LocaleTextView_localizedText
                )
                if (locale != null && localizedText != null) {
                    typedArray.recycle()
                }
            }
        }
    }

    /**
     * Updates the MaterialTextView super-class's text property with a LocaleSpan wrapping the
     * localizeText with the specified locale. Only sets the text if locale and localizedText are
     * non-null, so it does not clear existing text if those fields are null.
     */
    private fun updateText() {
        locale?.let { locale ->
            localizedText?.let { baseText ->
                // Key technique: Uses LocaleSpan; see function and class-level KDoc for details.
                val wrappedText = SpannableString(baseText)
                wrappedText.setSpan(
                    LocaleSpan(locale),0,
                    wrappedText.length,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
                this.text = wrappedText
            }
        }
    }

    /**
     * Sets the custom view's Locale property using an IETF BCP 47 language tag string, such as
     * "en", "es", or "de". Sets the custom control's Locale to null if given a null or invalid
     * languageTag value.
     */
    fun setLocale(languageTag: String?) {
        this.locale = languageTag?.let {
            Locale.forLanguageTag(it)
        }
    }
}