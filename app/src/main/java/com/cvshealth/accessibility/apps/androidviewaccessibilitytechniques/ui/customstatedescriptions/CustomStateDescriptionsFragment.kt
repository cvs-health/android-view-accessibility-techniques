package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.customstatedescriptions

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
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentCustomStateDescriptionsBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment with examples of adequate and good practices of labeling control states to improve
 * screen reader user experience.
 *
 * The generally adequate technique used in Examples 1 and 3 is allowing the default accessibility
 * state description label for a stateful control to be used (by doing nothing to affect it).
 * In this case, the TalkBack screen reader will announce "Checked" or "Not checked" for a CheckBox
 * and "ON" or "OFF" for a Switch.
 *
 * The key technique of Examples 2 and 4 is applying ViewCompat.setStateDescription() to each
 * stateful View (i.e., Checkbox or Switch). Note how the state description message is changed
 * based on the current control state.
 *
 * The Switch in Example 4 also overrides the standard click action description using an
 * AccessibilityDelegate. Although a separate technique, this approach dovetails with customizing
 * the state description in making the Switch's announcements in the TalkBack screen reader far more
 * specific to this control's intent, and thus improves the screen reader user experience. The click
 * action message is also changed based on the current control state.
 *
 * Note that ViewCompat.setStateDescription() does not work on all Android platforms; however, in
 * those cases the default state announcements will be employed, which is adequate.
 *
 * See also the associated layout file fragment_custom_state_descriptions.xml.
 */
class CustomStateDescriptionsFragment : Fragment() {

    private var _binding: FragmentCustomStateDescriptionsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comments below.

        val viewModel =
            ViewModelProvider(this).get(CustomStateDescriptionsViewModel::class.java)

        _binding = FragmentCustomStateDescriptionsBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample3Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample4Heading.setAsAccessibilityHeading()

        // Update the checkbox custom state description when state changes
        viewModel.customizedCheckboxState.observe(viewLifecycleOwner) { checkboxState ->
            setCurrentStateDescription(
                binding.checkboxWithCustomizedStateDescriptions,
                currentCheckboxStateDescriptionId(checkboxState)
            )
        }

        binding.checkboxWithCustomizedStateDescriptions.setOnClickListener {
            viewModel.toggleCustomizedCheckbox()
        }

        viewModel.shieldsState.observe(viewLifecycleOwner) { shieldsState ->
            // Update the Switch custom state description when state changes
            setCurrentStateDescription(
                binding.switchWithCustomizedStateDescriptions,
                currentShieldsStateDescriptionId(shieldsState)
            )

            binding.switchWithCustomizedStateDescriptions.accessibilityDelegate = object : View.AccessibilityDelegate() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfo
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)

                    // Extra technique: customize the standard click action description to reflect the current switch state
                    info.addAction(
                        AccessibilityAction(
                            AccessibilityAction.ACTION_CLICK.getId(),
                            getString(currentShieldsClickActionDescriptionId(shieldsState))
                        )
                    )

                    // Alternative approach to customizing state description:
                    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    //     info?.stateDescription =
                    //         getString(currentShieldsStateDescriptionId(shieldsState))
                    // }
                }
            }
        }

        binding.switchWithCustomizedStateDescriptions.setOnClickListener {
            viewModel.toggleShields()
        }

        return binding.root
    }

    private fun setCurrentStateDescription(statefulView: View, stateDescriptionId: Int) {
        // Key technique: Set a UI element's custom state description
        ViewCompat.setStateDescription(
            statefulView,
            getString(stateDescriptionId)
        )
    }

    private fun currentCheckboxStateDescriptionId(currentState: Boolean) = if (currentState)
        R.string.custom_state_descriptions_checked_label
    else
        R.string.custom_state_descriptions_unchecked_label

    private fun currentShieldsStateDescriptionId(currentState: Boolean) = if (currentState)
        R.string.custom_state_descriptions_shields_raised
    else
        R.string.custom_state_descriptions_shields_lowered

    private fun currentShieldsClickActionDescriptionId(currentState: Boolean) = if (currentState)
        R.string.custom_state_descriptions_shields_deactivate_click_label
    else
        R.string.custom_state_descriptions_shields_activate_click_label

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}