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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.focusablecontrols

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentFocusableControlsBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with examples of bad and good practices of creating keyboard-focusable controls. These
 * techniques support one aspect of the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#keyboard">Success Criterion 2.1.1 Keyboard</a>,
 * as well as <a href="https://www.w3.org/TR/WCAG21/#pointer-cancellation">Success Criterion 2.5.2 Pointer Cancellation</a>.
 *
 * One key technique involved is using onClickListener() instead of onTouchListener(), because
 * onClickListener() is keyboard-enabled and supports pointer cancellation, unlike onTouchListener().
 *
 * The other key technique, shown in the associated layout file, fragment_focusable_controls.xml,
 * is setting android:focusable="true" on all clickable elements. This technique enhances
 * compatibility with older versions of Android.
 */
class FocusableControlsFragment : Fragment()  {
    private var _binding: FragmentFocusableControlsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, function-level KDoc below, and associated XML layout file.

        _binding = FragmentFocusableControlsBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        // Bad examples: These controls use an onTouchListener, which has no keyboard or accessibility support. Don't do this.
        // Note the Android Lint warning about ClickableViewAccessibility.
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.imageViewExample1.onTouchShowSnackbar(R.string.focusable_controls_example_1_message)

        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        binding.imageViewExample2.onTouchShowSnackbar(R.string.focusable_controls_example_2_message)

        binding.linearLayoutExample3Heading.setAsAccessibilityHeading()
        binding.imageButtonExample3.onTouchShowSnackbar(R.string.focusable_controls_example_3_message)

        // Good examples: These controls use an onClickListener, which provides keyboard and accessibility support.
        binding.linearLayoutExample4Heading.setAsAccessibilityHeading()
        binding.imageViewExample4.onClickShowSnackbar(R.string.focusable_controls_example_4_message)

        binding.linearLayoutExample5Heading.setAsAccessibilityHeading()
        binding.imageButtonExample5.onClickShowSnackbar(R.string.focusable_controls_example_5_message)

        return binding.root
    }

    /**
     * Sets an onTouchListener on a View that pops up a Snackbar displaying the given message
     * resource string. Does not support keyboard or accessibility services, only touch.
     * Avoid using onTouchListener, if at all possible.
     *
     * Note the Android Lint warning about not invoking performClick() in the listener lambda.
     */
    private fun View.onTouchShowSnackbar(@StringRes msgId: Int) {
        // Avoid using this method if possible. See KDocs for details.
        setOnTouchListener { v: View, motionEvent: MotionEvent ->
            val message = getString(msgId)
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
            return@setOnTouchListener true
        }
    }

    /**
     * Sets an onClickListener on a View that pops up a Snackbar displaying the given message
     * resource string. Supports keyboard and accessibility services. Prefer using onClickListener
     * whenever possible.
     */
    private fun View.onClickShowSnackbar(@StringRes msgId: Int) {
        // Key technique: Prefer onClickListener; see KDocs for details.
        setOnClickListener {
            val message = getString(msgId)
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        }
    }
}