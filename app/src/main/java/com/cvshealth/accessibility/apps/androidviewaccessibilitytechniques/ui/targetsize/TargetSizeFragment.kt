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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.targetsize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentTargetSizeBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with problematic and good examples of keyboard focus indicator practices. These
 * techniques support WCAG <a href="https://www.w3.org/TR/WCAG22/#target-size">Success Criterion 2.5.5 Target Size</a> (Level AAA),
 * and <a href="https://www.w3.org/TR/WCAG22/#target-size-minimum">Success Criterion 2.5.8 Target Size (Minimum)</a> (Level AA),
 * and Material Design guidelines <a href="https://m2.material.io/design/usability/accessibility.html#layout-and-typography">Touch and pointer targets</a>.
 *
 * See the associated XML layout file, fragment_target_size.xml, for all important techniques.
 */
class TargetSizeFragment : Fragment()  {
    private var _binding: FragmentTargetSizeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc and associated XML layout file.

        _binding = FragmentTargetSizeBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample3Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample4Heading.setAsAccessibilityHeading()

        binding.imageButtonExample1.onClickShowSnackbar(R.string.target_size_example_1_message)
        binding.imageButtonExample2.onClickShowSnackbar(R.string.target_size_example_2_message)
        binding.imageButtonExample3.onClickShowSnackbar(R.string.target_size_example_3_message)
        binding.imageButtonExample4.onClickShowSnackbar(R.string.target_size_example_4_message)

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