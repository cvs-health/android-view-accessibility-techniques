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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.keyboardtype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentKeyboardTypeBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment with bad and good examples of setting the soft keyboard type of input controls in
 * support of the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#identify-purpose">Success Criterion 1.3.5 Identify Input Purpose</a>.
 *
 * See the associated XML layout file, fragment_keyboard_type.xml, for the key technique of
 * applying android:inputType to input fields.
 *
 * For more information about keyboard types, see <a href="https://developer.android.com/develop/ui/views/touch-and-input/keyboard-input/style">Specify the input method type</a>.
 */
class KeyboardTypeFragment : Fragment() {

    private var _binding: FragmentKeyboardTypeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc and associated XML layout file.

        _binding = FragmentKeyboardTypeBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample3Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample4Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample5Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample6Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample7Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample8Heading.setAsAccessibilityHeading()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}