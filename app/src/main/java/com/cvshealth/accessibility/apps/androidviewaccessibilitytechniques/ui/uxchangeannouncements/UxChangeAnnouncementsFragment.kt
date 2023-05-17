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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.uxchangeannouncements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentUxChangeAnnouncementsBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import kotlin.time.Duration.Companion.seconds

/**
 * Fragment with good examples of announcing changes to the visible user experience to Accessibility
 * Services like the TalkBack screen reader. These techniques support the WCAG 2.x
 * <a href="https://www.w3.org/TR/WCAG21/#status-messages">Success Criterion 4.1.3 Status Messages</a>.
 *
 * One key technique is using android:accessibilityLiveRegion to announce field value changes or
 * when a control becomes visible. This is shown in the associated XML layout file:
 * fragment_ux_change_announcement.xml.
 *
 * The other key technique is using View.announceForAccessibility(), used here to announce when a
 * control is hidden and which has broader applications.
 */
class UxChangeAnnouncementsFragment : Fragment() {

    private var _binding: FragmentUxChangeAnnouncementsBinding? = null

    // Note: This is not how mutable state should be handled. Add a ViewModel.
    var counter = 0

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comments below.

        _binding = FragmentUxChangeAnnouncementsBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        // Good example 1: Counter with accessibilityLiveRegion
        // The key technique of Example 1 is setting android:accessibilityLiveRegion="polite" in
        // fragment_ux_change_announcement.xml so each change to the textCounter value is announced
        // to Accessibility Services like TalkBack.
        binding.linearLayoutExampleCounterHeading.setAsAccessibilityHeading()
        binding.textCounter.text = getString(R.string.ux_change_announcements_counter, counter)
        binding.buttonIncrementCounter.setOnClickListener {
            counter++
            binding.textCounter.text = getString(R.string.ux_change_announcements_counter, counter)
        }
        binding.buttonResetCounter.setOnClickListener {
            counter = 0
            binding.textCounter.text = getString(R.string.ux_change_announcements_counter, counter)
        }

        // Good example 2: Waiting indicator with accessibilityLiveRegion and announceForAccessibility
        //
        // Example 2's first key technique is the use of android:accessibilityLiveRegion on the
        // waiting indicator ContentLoadingProgressBar (binding.progressBarWaitingIndicator) to
        // announce its appearance. This is shown in the layout fragment_ux_change_announcement.xml.
        //
        // Example 2's second key technique is the use of View.announceForAccessibility() to
        // announce when the waiting indicator is hidden.
        binding.linearLayoutExampleWaitingIndicatorHeading.setAsAccessibilityHeading()
        binding.buttonShowWaitingIndicator.setOnClickListener {
            binding.progressBarWaitingIndicator.show()
            // Disable all other controls
            binding.buttonShowWaitingIndicator.isEnabled = false
            binding.buttonIncrementCounter.isEnabled = false
            binding.buttonResetCounter.isEnabled = false
            // Set timer to dismiss waiting indicator and re-enable buttons.
            // Uses postDelayed for demonstration purposes. In real life, this would be done by an
            // asynchronous call in the ViewModel and observing a LiveData here in the Fragment.
            it.postDelayed(
                {
                    if (isResumed) {
                        binding.progressBarWaitingIndicator.announceForAccessibility(
                            getString(R.string.ux_change_announcements_waiting_completed)
                        )
                        binding.progressBarWaitingIndicator.hide()
                        // Enable all other controls
                        binding.buttonShowWaitingIndicator.isEnabled = true
                        binding.buttonIncrementCounter.isEnabled = true
                        binding.buttonResetCounter.isEnabled = true
                    }
                },
                15.seconds.inWholeMilliseconds
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}