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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.inlinelinks

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentInlineLinksBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment with good examples of creating in-line links that announce their name, role, and value
 * in accordance with the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#name-role-value">Success Criterion 4.1.2 Name, Role, Value</a>.
 * The primary goal is for TalkBack to make a Links menu available when the associated text block is
 * announced.
 *
 * See also the associated XML layout file, fragment_inline_links.xml, and res/values/strings.xml.
 */
class InlineLinksFragment  : Fragment() {

    private var _binding: FragmentInlineLinksBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comments below.

        _binding = FragmentInlineLinksBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.linearLayoutExample1HeadingBar.setAsAccessibilityHeading()
        binding.linearLayoutExample2HeadingBar.setAsAccessibilityHeading()

        // The key techniques of Example 1 are embedding an HTML anchor tag in string resource
        // used for the text of this TextViews (see the associated layout file
        // fragment_inline_links.xml and res/values/strings.xml) and (2) setting this TextView
        // control's movementMethod to an instance of LinkMovementMethod (as shown here).
        binding.textExample1.movementMethod = LinkMovementMethod.getInstance()

        // The key techniques of Example 2 are creating URLSpan sections within this TextView's text
        // and setting its movementMethod to an instance of LinkMovementMethod.
        // A more robust implementation would use URL pattern parsing or marker text instead
        // of hard-coded URLSpan start and end values.
        val example2Text = SpannableString(resources.getText(R.string.inline_links_example_2_text))
        example2Text.setSpan(URLSpan("https://developer.android.com/reference/android/text/style/URLSpan"), 44, 51, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        example2Text.setSpan(URLSpan("https://support.google.com/accessibility/android/answer/6378148?hl=en"), 65, 87, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.textExample2.text = example2Text
        binding.textExample2.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}