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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.contentgroupreplacement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentContentGroupReplacementBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment with examples of bad and good practices of replacing grouped content to improve screen
 * reader user experience.
 *
 * The key technique of Example 3 is shown below. Additional discussion of all examples is in the
 * associated layout file: fragment_content_group_replacement.xml.
 */
class ContentGroupReplacementFragment  : Fragment() {

    private var _binding: FragmentContentGroupReplacementBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comment below.

        _binding = FragmentContentGroupReplacementBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        binding.linearLayoutTextRatingGroupUngroupedHeading.setAsAccessibilityHeading()
        val rating = 3.4f
        val maxRating = 5
        binding.ratingbarUngrouped.rating = rating
        binding.ratingbarUngrouped.numStars = maxRating
        binding.ratingbarGrouped.rating = rating
        binding.ratingbarGrouped.numStars = maxRating
        binding.ratingbarCustomized.rating = rating
        binding.ratingbarCustomized.numStars = maxRating

        binding.linearLayoutTextRatingGroupDefaultHeading.setAsAccessibilityHeading()
        val ratingsText = getString(R.string.content_group_replacement_rating_text, rating.toString(), maxRating.toString())
        binding.ratingValueUngrouped.text = ratingsText
        binding.ratingValueGrouped.text = ratingsText
        binding.textRatingValueCustomized.text = ratingsText


        binding.linearLayoutTextRatingGroupCustomizedHeading.setAsAccessibilityHeading()
        val reviews = 856
        val reviewsText = getString(R.string.content_group_replacement_reviews, reviews)
        binding.reviewsUngrouped.text = reviewsText
        binding.reviewsGrouped.text = reviewsText
        binding.reviewsCustomized.text = reviewsText

        // Key technique for example 3: set the enclosing layout's contentDescription to a localized
        // plurals string containing all of the appropriate data values as substitution parameters.
        binding.ratingSectionCustomized.contentDescription = resources.getQuantityString(
            R.plurals.content_group_replacement_rating_group_content_description,
            reviews,
            rating.toString(),
            maxRating,
            reviews
        )

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}