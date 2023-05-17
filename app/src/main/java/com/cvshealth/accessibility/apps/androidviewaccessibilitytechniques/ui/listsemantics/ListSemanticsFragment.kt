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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.listsemantics

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.addListItemSemantics
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.addListSemantics
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentListSemanticsBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

const val GAP_SIZE = 8 // in dp

/**
 * Fragment with examples of bad and good practices regarding list semantics. These techniques
 * support one aspect of the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#info-and-relationships">Success Criterion 1.3.1 Info and Relationships</a>.
 *
 * The key techniques here are illustrated by createBulletSpan() below and the extension functions
 * LinearLayout.addListSemantics(size: Int) and View.addListItemSemantics(index: Int)
 * from AccessibilityHelper.kt.
 *
 * Other key techniques are illustrated in the associated layout file: fragment_list_semantics.xml.
 */
class ListSemanticsFragment  : Fragment() {

    private var _binding: FragmentListSemanticsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comments below.

        _binding = FragmentListSemanticsBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        // Bad example 1: Visual list without list semantics
        // Does show the technique of creating bullet lists with BulletSpan using createBulletSpan().
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.textPoint1WithoutSemantics.text =  createBulletSpan(R.string.list_semantics_bad_point_1)
        binding.textPoint2WithoutSemantics.text =  createBulletSpan(R.string.list_semantics_bad_point_2)
        binding.textPoint3WithoutSemantics.text =  createBulletSpan(R.string.list_semantics_bad_point_3)

        // Good example 2: Bullet list with list semantics
        // The key technique in Example 2 is the use of the LinearLayout.addListSemantics(size: Int)
        // extension function from AccessibilityHelper.kt. This extension function applies an
        // AccessibilityDelegate to the LinearLayout that sets its AccessibilityNodeInfo's
        // collectionInfo property.
        //
        // (Note that list item semantics are not added so that the list items remain unnumbered
        // when read by TalkBack.)
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        binding.linearlayoutWithUnnumberedListSemantics.addListSemantics(3)
        binding.textPoint1WithSemantics.text = createBulletSpan(R.string.list_semantics_good_point_1)
        binding.textPoint2WithSemantics.text = createBulletSpan(R.string.list_semantics_good_point_2)
        binding.textPoint3WithSemantics.text = createBulletSpan(R.string.list_semantics_good_point_3)

        // Good example 3: Numbered list with list semantics
        // The key techniques in Example 3 are the use of the extension functions
        // LinearLayout.addListSemantics(size: Int) and View.addListItemSemantics(index: Int)
        // from AccessibilityHelper.kt. addListSemantics operates as described in Example 2.
        // The addListItemSemantics() extension function applies an AccessibilityDelegate to each
        // list item View that sets AccessibilityNodeInfo's collectionItemInfo property. That
        // property allows Accessibility Services like the TalkBack screen reader to announce each
        // items position within the list, which is appropriate for a numbered list.
        //
        // (Although it is not used here, NumberSpan can be very useful in formatting numbered list
        // items. NumberSpan is especially useful for producing correct alignment of longer texts
        // indented from their item numbers. Be sure to provide the item number in each item's
        // contentDescription, as TalkBack does not read a NumberSpan's item number.)
        binding.linearLayoutExample3Heading.setAsAccessibilityHeading()
        binding.linearlayoutWithNumberedListSemantics.addListSemantics(3)
        binding.textPoint1WithNumberedSemantics.addListItemSemantics(0)
        binding.textPoint2WithNumberedSemantics.addListItemSemantics(1)
        binding.textPoint3WithNumberedSemantics.addListItemSemantics(2)

        // Good example 4: RecyclerView list with list semantics
        // The key technique of Example 4 is simply using a RecyclerView for a visual list, as
        // RecyclerView's provide list semantics automatically.
        binding.linearLayoutExample4Heading.setAsAccessibilityHeading()
        val contactListAdapter = ContactListAdapter()
        binding.recyclerViewSemanticList.adapter = contactListAdapter
        contactListAdapter.submitList(contactDataSource)

        // Good example 5: Non-list RecyclerView without list semantics
        // List semantics is not appropriate when a RecylerView is used to show data that is not
        // presented as a visual list. RecyclerView is sometimes used for performance reasons to
        // render dynamic heterogeneous data. It can be better to disable its list semantics in
        // those cases to prevent Assistive Technologies, like TalkBack, from misleading users by
        // announcing that data is part of a list when it really isn't.
        //
        // The key technique used to disable list semantics in Example 5 is to set
        // android:importantForAccessibility="no" on the RecyclerView. This is done in the layout
        // XML; see fragment_list_semantics.xml.
        //
        // Note that this technique may have negative effects on the automatic scrolling of the
        // list in TalkBack. Manually test this technique before adopting it for your use case.
        binding.linearLayoutExample5Heading.setAsAccessibilityHeading()
        val dynamicContentAdapter = DynamicContentAdapter()
        binding.recyclerViewNonSemanticList.adapter = dynamicContentAdapter
        dynamicContentAdapter.submitList(dynamicContentDataSource())
        dynamicContentAdapter.submitList(dynamicContentDataSource())

        return binding.root
    }

    /**
     * Wraps an externalized string resource value in a BulletSpan for presentation in a bullet
     * list.
     */
    private fun createBulletSpan(@StringRes stringId: Int): SpannableStringBuilder {
        val span = SpannableStringBuilder(getString(stringId))
        span.setSpan(
            BulletSpan(context?.dpToPx(GAP_SIZE) ?: GAP_SIZE),
            0,
            span.length,
            SPAN_INCLUSIVE_INCLUSIVE
        )
        return span
    }

    private fun Context.dpToPx(dp: Int): Int =
        (dp * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dynamicContentDataSource(): List<DynamicContent> = listOf(
        DynamicContent(
            type = DynamicContentType.Heading,
            heading = getString(R.string.list_semantics_dynamic_content_1_heading)
        ),
        DynamicContent(
            type = DynamicContentType.Article,
            title = getString(R.string.list_semantics_dynamic_content_2_title),
            author = getString(R.string.list_semantics_dynamic_content_2_author),
            description = getString(R.string.list_semantics_dynamic_content_2_description)
        ),
        DynamicContent(
            type = DynamicContentType.Article,
            title = getString(R.string.list_semantics_dynamic_content_3_title),
            subtitle = getString(R.string.list_semantics_dynamic_content_3_subtitle),
            author = getString(R.string.list_semantics_dynamic_content_3_author),
        ),
        DynamicContent(
            type = DynamicContentType.Heading,
            heading = getString(R.string.list_semantics_dynamic_content_4_heading),
            description = getString(R.string.list_semantics_dynamic_content_4_description)
        )
    )
}

