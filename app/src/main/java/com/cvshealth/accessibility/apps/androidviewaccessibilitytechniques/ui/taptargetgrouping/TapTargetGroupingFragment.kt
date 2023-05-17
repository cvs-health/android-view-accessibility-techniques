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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.taptargetgrouping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentTapTargetGroupingBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with examples of bad and good practices of grouping tap targets together.
 * These techniques support the WAI Mobile Task Force guidance about
 * <a href="https://w3c.github.io/Mobile-A11y-TF-Note/#grouping-operable-elements-that-perform-the-same-action">Grouping operable elements that perform the same action</a>.
 *
 * One key technique of Example 2 is applying the click handler to the entire layout below, instead
 * of applying a click handler to each element of the layout as is done in Example 1. The other key
 * techniques are demonstrated in the associated layout file: fragment_tap_target_grouping.xml.
 */
class TapTargetGroupingFragment : Fragment()  {
    private var _binding: FragmentTapTargetGroupingBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comment below.

        _binding = FragmentTapTargetGroupingBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.imageButtonExample1.onClickShowSnackbar(
            R.string.tap_target_grouping_ungrouped_target_snackbar_message)
        binding.textClickableExample1.onClickShowSnackbar(
            R.string.tap_target_grouping_ungrouped_target_snackbar_message)

        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        // Key technique for Example 2: Apply the click handler to the enclosing layout.
        // See class-level KDoc for more information.
        binding.linearLayoutExample2.onClickShowSnackbar(
            R.string.tap_target_grouping_grouped_target_snackbar_message)
        // Additional technique: Disable the Example 2 ImageButton being focusable with the
        // keyboard, which is required here despite all the settings in the layout file designed to
        // prevent that control from being focusable. This problem is likely a result of button
        // class initialization code setting that focusability after the control's inflation. This
        // code happens last, so it determines the final UI state.
        binding.imageButtonExample2.isFocusable = false

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