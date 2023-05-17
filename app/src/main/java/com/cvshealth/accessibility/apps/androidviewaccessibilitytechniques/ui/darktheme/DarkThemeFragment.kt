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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.darktheme

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentDarkThemeBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment describing how this application supports dark and light themes in accordance with the
 * WAI Mobile Task Force guidance about <a href="https://w3c.github.io/Mobile-A11y-TF-Note/#support-the-characteristic-properties-of-the-platform">supporting the characteristic properties of the platform</a>
 * (in this case, the "Dark theme" setting).
 *
 * No techniques for dark theme support are demonstrated on this page or in its associated XML
 * layout file. See instead AndroidManifest.xml (for the application's theme setting),
 * values/themes.xml, values-dark/themes.xml, values/colors.xml, and values-dark/colors.xml.
 */
class DarkThemeFragment  : Fragment() {

    private var _binding: FragmentDarkThemeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc and the files referenced there.

        _binding = FragmentDarkThemeBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.textDescription1.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}