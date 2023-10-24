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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.focusvisibility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentFocusVisibilityBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with problematic and good examples of keyboard focus indicator practices. These
 * techniques support the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#focus-visible">Success Criterion 2.4.7 Focus Visible</a>
 * and the WCAG 2.2 <a href="https://www.w3.org/TR/WCAG22/#focus-appearance">Success Criterion 2.4.13 Focus Appearance</a> (Level AAA).
 *
 * See the associated XML layout file, fragment_focus_visibility.xml, for all important techniques.
 */
class FocusVisibilityFragment : Fragment() {

    private var _binding: FragmentFocusVisibilityBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc and associated XML layout file.

        _binding = FragmentFocusVisibilityBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample3Heading.setAsAccessibilityHeading()
        binding.buttonExample1.onClickShowSnackbar(R.string.focus_visibility_example_1_button_message)
        binding.buttonExample2.onClickShowSnackbar(R.string.focus_visibility_example_2_button_message)
        binding.imageButtonExample4.onClickShowSnackbar(R.string.focus_visibility_example_4_image_button_message)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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