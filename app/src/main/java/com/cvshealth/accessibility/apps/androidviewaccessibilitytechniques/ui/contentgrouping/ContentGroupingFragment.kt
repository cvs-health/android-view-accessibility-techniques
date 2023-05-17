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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.contentgrouping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentContentGroupingBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with examples of bad and good practices of grouping content together to improve screen
 * reader user experience.
 *
 * One key technique of Example 6 is applying a click handler to its CardView (binding.card3 below).
 * The other key techniques are demonstrated in the associated layout file:
 * fragment_content_grouping.xml.
 */
class ContentGroupingFragment  : Fragment() {

    private var _binding: FragmentContentGroupingBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comment below.

        _binding = FragmentContentGroupingBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.textExampleGroup1.setAsAccessibilityHeading()
        binding.textExampleGroup2.setAsAccessibilityHeading()

        // Key technique: Add accessibility grouping to third card example by applying a click listener.
        binding.card3.setOnClickListener {
            val message = getString(R.string.content_grouping_card3_message)
            Snackbar
                .make(binding.root, message, Snackbar.LENGTH_LONG)
                .show()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}