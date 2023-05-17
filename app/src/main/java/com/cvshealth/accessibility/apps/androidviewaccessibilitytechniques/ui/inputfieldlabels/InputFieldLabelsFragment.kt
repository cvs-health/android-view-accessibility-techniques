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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.inputfieldlabels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentInputFieldLabelsBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment with examples of bad and good practices of programmatically associating labels with
 * input fields. These techniques support the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#info-and-relationships">Success Criterion 1.3.1 Info and Relationships</a>.
 *
 * All of the key techniques are demonstrated in the associated layout file
 * fragment_input_field_labels.xml.
 */
class InputFieldLabelsFragment : Fragment() {

    private var _binding: FragmentInputFieldLabelsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc and associated XML layout file.

        _binding = FragmentInputFieldLabelsBinding.inflate(inflater, container, false)
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
        binding.linearLayoutExample11Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample12Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample13Heading.setAsAccessibilityHeading()
        binding.rangeSliderAssociated.values = listOf(20.0f, 50.0f)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}