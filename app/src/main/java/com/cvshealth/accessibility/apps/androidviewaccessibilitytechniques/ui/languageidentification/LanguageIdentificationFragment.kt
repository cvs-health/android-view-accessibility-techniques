/*
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
 */
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.languageidentification

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.LocaleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentLanguageIdentificationBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import java.util.Locale

/**
 * Fragment with examples of bad and good practices in identifying texts that differ from the
 * system-configured language. Without appropriate handling, texts in different languages will be
 * mispronounced by the text-to-speech engine within the TalkBack screen reader, reducing
 * accessibility. These techniques support the WCAG <a href="https://www.w3.org/TR/WCAG22/#language-of-parts">Success Criterion 3.1.2 Language of Parts</a>.
 *
 * Also incorporate Google's guidance about <a href="https://developer.android.com/guide/topics/resources/app-languages">per-app language preferences</a>
 * that is available for Android 13+ or with Appcompat 1.6+, which is yet to be done in this sample
 * application.
 */
class LanguageIdentificationFragment : Fragment()  {
    private var _binding: FragmentLanguageIdentificationBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comments below.
        val viewModel =
            ViewModelProvider(this).get(LanguageIdentificationViewModel::class.java)

        _binding = FragmentLanguageIdentificationBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()

        // Key technique for Example 2: Use a LocaleSpan to mark text blocks in different
        // languages and insert the resulting SpannableString as the TextView's text.
        // This example is deliberately simplified by hard-coding the languages and the expected
        // number of texts. In production-grade code, mixed-language text is generally implemented
        // using specific markup in strings to indicate the mixed-language chunks and their
        // respective languages.
        val exampleText = getText(R.string.language_identification_example_text)
        val wrappedExampleText = SpannableString(exampleText)
        val firstQuotationMarkLocation = exampleText.indexOf('"')
        val secondQuotationMarkLocation = exampleText.indexOf('"', firstQuotationMarkLocation + 1)
        val thirdQuotationMarkLocation = exampleText.indexOf('"', secondQuotationMarkLocation + 1)
        wrappedExampleText.setSpan(
            LocaleSpan(Locale.FRENCH),
            firstQuotationMarkLocation,
            secondQuotationMarkLocation + 1,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        wrappedExampleText.setSpan(
            LocaleSpan(Locale.ENGLISH),
            thirdQuotationMarkLocation,
            wrappedExampleText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.textExample2.text = wrappedExampleText

        // Key technique for Example 3: Uses a custom view that wraps text in a LocaleSpan.
        // See LocaleTextView.kt and layout/fragment_language_identification.xml for details.

        // Set the Example 3b custom control's locale and localizedText from ViewModel data.
        viewModel.localizedLanguageDataState.observe(viewLifecycleOwner) { localizedLanguageDataState ->
            binding.localeTextViewExample3b.setLocale(localizedLanguageDataState.localeLanguageTag)
            binding.localeTextViewExample3b.localizedText = localizedLanguageDataState.localizedText
        }
        viewModel.initStateData()

        return binding.root
    }
}