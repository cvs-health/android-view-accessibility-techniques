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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.inputerrorannouncements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentInputErrorAnnouncementsBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with good examples of visibly displaying and announcing errors to Accessibility Services
 * like the TalkBack screen reader. These techniques support the WCAG 2.x
 * <a href="https://www.w3.org/TR/WCAG21/#error-identification">Success Criterion 3.3.1 Error Identification</a>,
 * <a href="https://www.w3.org/TR/WCAG21/#error-suggestion">Success Criterion 3.3.3 Error Suggestion</a>,
 * and <a href="https://www.w3.org/TR/WCAG21/#status-messages">Success Criterion 4.1.3 Status Messages</a>.
 *
 * Two key techniques are using app:errorEnabled="true" on TextInputLayout elements to enable error
 * message display and setting the TextInputLayout error property to any applicable error message.
 *
 * Another key technique is properly managing focus after errors. This is done using
 * performAccessibilityAction(ACTION_ACCESSIBILITY_FOCUS, ...) and
 * sendAccessibilityEvent(TYPE_VIEW_FOCUSED). Best practice is put accessibility focus on the first
 * field in error (but not necessarily to move keyboard focus, which would be done with
 * requestFocus()). See handleErrorResult() below.
 *
 * See also the associated XML layout file: fragment_input_error_announcements.xml.
 */
class InputErrorAnnouncementsFragment : Fragment() {

    private var _binding: FragmentInputErrorAnnouncementsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comments below.

        val viewModel =
            ViewModelProvider(this).get(InputErrorAnnouncementsViewModel::class.java)

        _binding = FragmentInputErrorAnnouncementsBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        binding.textinputedittextName.addTextChangedListener {
            binding.textinputlayoutName.error = ""
        }

        binding.textinputedittextPassword.addTextChangedListener {
            binding.textinputlayoutPassword.error = ""
        }
        binding.textinputedittextPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onDone(viewModel)
                true
            } else
                false
        }

        binding.buttonSubmit.setOnClickListener {
            onDone(viewModel)
        }

        viewModel.validationState.observe(viewLifecycleOwner) { validationResults ->
            if (validationResults != null) {
                when (validationResults) {
                    is ValidationResults.PendingResult -> {
                        // no-op - would normally start a waiting spinner
                    }
                    is ValidationResults.SuccessResult -> handleSuccessResult()
                    is ValidationResults.ErrorResult -> handleErrorResult(validationResults)
                }
            }
        }

        return binding.root
    }

    private fun onDone(viewModel: InputErrorAnnouncementsViewModel) {
        viewModel.validate(
            binding.textinputedittextName.text.toString(),
            binding.textinputedittextPassword.text.toString()
        )
    }

    private fun handleSuccessResult() {
        binding.textinputlayoutName.error = ""
        binding.textinputlayoutPassword.error = ""
        val message = getString(R.string.input_error_announcements_successful_results)
        Snackbar
            .make(binding.rootLayout, message, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun handleErrorResult(validationResults: ValidationResults.ErrorResult) {
        if (validationResults.isNameInError) {
            binding.textinputlayoutName.apply {
                // Key technique: Put accessibility focus on the first field in error.
                // Keyboard focus remains on the Submit button.
                editText?.text?.clear()
                editText?.performAccessibilityAction(
                    AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS,
                    null
                )
                editText?.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                // Key technique: Leverage the TextInputLayout error property, which announces itself politely.
                // Delay the error announcement until after the focus change.
                post {
                    error = getString(R.string.input_error_announcements_name_field_error)
                }
            }
        } else {
            // If the field value is valid, clear any prior error state.
            binding.textinputlayoutName.error = ""
        }

        if (validationResults.isPasswordInError) {
            binding.textinputlayoutPassword.apply {
                // Key technique: Put accessibility focus on this field if it is in error and if a
                // Name field error doesn't take precedence. Keyboard focus remains on the Submit button.
                if (!validationResults.isNameInError) {
                    editText?.text?.clear()
                    editText?.performAccessibilityAction(
                        AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS,
                        null
                    )
                    editText?.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                }
                // Key technique: Leverage the TextInputLayout error property, which announces itself politely.
                // Delay the error announcement until after the focus change.
                post {
                    error = getString(R.string.input_error_announcements_password_field_error)
                }
            }
        } else {
            // If the field value is valid, clear any prior error state.
            binding.textinputlayoutPassword.error = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}