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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.orientation

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentOrientationBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment describing how this application support both portrait and landscape orientations
 * in accordance with the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#orientation">Success Criterion 1.3.4 Orientation</a>.
 *
 * No techniques for orientation support are demonstrated on this page or in its associated XML
 * layout file. See instead AndroidManifest.xml (for the lack of
 * android:screenOrientation="portrait" on any <activity/> element) and the
 * <a href="https://developer.android.com/guide/topics/manifest/activity-element.html#screen">android:screenOrientation</a>
 * documentation.
 *
 * Note that all layout/fragment_*.xml files include ScrollView, so they can display their entire content without
 * regard to any restrictions to the viewport's vertical direction.
 *
 * Also, examine UI packages such as Home with ViewModels that preserve application state across
 * configuration changes and see
 * <a href="https://developer.android.com/guide/topics/resources/runtime-changes">Handle configuration changes</a>
 * for more details.
 */
class OrientationFragment  : Fragment() {

    private var _binding: FragmentOrientationBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc. (One extra technique used mentioned in-line below.)

        _binding = FragmentOrientationBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        // Extra technique: Use LinkMovementMethod to support in-line link in on-screen text.
        binding.textDescription3.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}