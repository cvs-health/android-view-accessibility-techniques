/*
   Copyright 2024 CVS Health and/or one of its affiliates

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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.ratingbarcontrols

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentRatingBarControlsBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment with examples of good practices for creating RatingBar controls.
 *
 * One key technique of Example 1 is shown below: attaching an OnRatingBarListener. Other techniques
 * are shown in the associated layout file: fragment_rating_bar_controls.xml.
 */
class RatingBarControlsFragment : Fragment() {

    private var _binding: FragmentRatingBarControlsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line
        // comments below.

        _binding = FragmentRatingBarControlsBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()

        // Key technique (not illustrated): listen to dynamic sources of view state data to
        // initialize or update the RatingBar rating property value.

        // Key technique: Attach an OnRatingBarChangeListener to listen for changes to the RatingBar
        // control's value and update state holders, such as a ViewModel, and other controls' values
        // (as shown here with the textRatingValue TextView).
        binding.ratingbar1.setOnRatingBarChangeListener { ratingBar: RatingBar, rating: Float, fromUser: Boolean ->
            if (fromUser) {
                binding.textRatingValue.text =
                    getString(R.string.ratingbar_controls_rating_text, rating, ratingBar.numStars)
            }
        }

        // Set the initial value of other Views.
        binding.textRatingValue.text = getString(
            R.string.ratingbar_controls_rating_text,
            binding.ratingbar1.rating,
            binding.ratingbar1.numStars
        )

        return binding.root
    }
}