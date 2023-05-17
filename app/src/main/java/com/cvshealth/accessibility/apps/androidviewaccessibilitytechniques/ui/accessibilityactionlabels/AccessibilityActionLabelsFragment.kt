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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.accessibilityactionlabels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentAccessibilityActionLabelsBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment with examples of adequate and good practices of labeling standard accessibility actions
 * to improve screen reader user experience.
 *
 * The generally adequate technique used in Example 1 is to allow the default accessibility action
 * label for clickable elements to be used (by doing nothing to affect it). In this case, the
 * TalkBack screen reader will announce: "Double-tap to activate."
 *
 * The key technique of Example 2 is applying an AccessibilityDelegate to a button that overrides
 * the standard click action to add a customized screen reader label for that action. In this case,
 * the TalkBack screen reader will announce "Double-tap to Show details." Note how only the "Show
 * details" portion of this message is provided when overriding the standard click accessibility
 * action; the "Double-tap to ..." portion of the utterance is determined by the TalkBack screen reader
 * based on the click action itself.
 *
 * See also the associated layout file fragment_accessibility_action_labels.xml.
 */
class AccessibilityActionLabelsFragment : Fragment() {

    private var _binding: FragmentAccessibilityActionLabelsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comments below.

        _binding = FragmentAccessibilityActionLabelsBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()

        binding.buttonWithCustomizedClickActionMessage.accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfo
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                // Key technique: Replace the description of the standard accessibility action for click.
                // See class-level KDoc for more information.
                info.addAction(
                    AccessibilityAction(
                        AccessibilityAction.ACTION_CLICK.getId(),
                        getString(R.string.accessibility_action_labels_custom_action_label)
                    )
                )
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}