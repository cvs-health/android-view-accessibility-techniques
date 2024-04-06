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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.textalternatives

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentTextAlternativesBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with examples of bad and good practices of providing text alternatives for non-text
 * content. These techniques support the WCAG <a href="https://www.w3.org/TR/WCAG22/#non-text-content">Success Criterion 1.1.1 Non-Text Content</a>.
 *
 * All of the key techniques are demonstrated in the associated layout file:
 * fragment_text_alternatives.xml.
 */
class TextAlternativesFragment : Fragment()  {
    private var _binding: FragmentTextAlternativesBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc and associated XML layout file.

        _binding = FragmentTextAlternativesBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample3Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample4Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample5Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample6Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample7Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample8Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample9Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample10Heading.setAsAccessibilityHeading()

        binding.imageButtonExample5.onClickShowSnackbar(R.string.text_alternatives_example_5_message)
        binding.imageButtonExample6.onClickShowSnackbar(R.string.text_alternatives_example_6_message)

        return binding.root
    }

    /**
     * Sets an onClickListener on a View that pops up a Snackbar displaying the given message
     * resource string.
     */
    private fun View.onClickShowSnackbar(@StringRes msgId: Int) {
        setOnClickListener {
            val message = getString(msgId)
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        }
    }
}