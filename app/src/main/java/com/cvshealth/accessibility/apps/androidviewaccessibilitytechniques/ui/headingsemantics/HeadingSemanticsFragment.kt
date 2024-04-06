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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.headingsemantics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentHeadingSemanticsBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment with examples of bad and good practices regarding heading semantics. These techniques
 * support one aspect of the WCAG <a href="https://www.w3.org/TR/WCAG22/#info-and-relationships">Success Criterion 1.3.1 Info and Relationships</a>.
 *
 * One of the key techniques is demonstrated below: setting the accessibility heading flag in code
 * via the extension function setAsAccessibilityHeading() from AccessibilityHelper.kt.
 * That helper method applies ViewCompat.setAccessibilityDelegate to a View which sets the View's
 * AccessibilityNodeInfoCompat object's isHeading property to true.
 *
 * The other key technique (setting android:accessibilityHeading="true") is illustrated in the
 * associated layout file: fragment_heading_semantics.xml. (This technique applies only to API 28+.)
 *
 * Both of these techniques are applied to headings throughout this sample app.
 */
class HeadingSemanticsFragment  : Fragment() {

    private var _binding: FragmentHeadingSemanticsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comment below.

        _binding = FragmentHeadingSemanticsBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        // Key technique: Use this extension method to mark accessibility headings before API level 28. See KDoc and AccessibilityHelper.kt.
        binding.linearLayoutGoodHeading.setAsAccessibilityHeading()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}