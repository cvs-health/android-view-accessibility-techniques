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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.addListSemantics
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentHomeBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        viewModel.pageModel.observe(viewLifecycleOwner) { pageModel ->
            renderPage(pageModel, viewModel)
        }

        binding.linearLayoutSection1ExpandCollapseHeader.setAsAccessibilityHeading()
        binding.linearLayoutSection1ExpandCollapseHeader.setOnClickListener { viewModel.toggleSection(SectionId.Section1) }
        addAccessibilityActions(binding.linearLayoutSection1ExpandCollapseHeader, SectionId.Section1, viewModel, sectionExpanded = false)
        binding.linearLayoutSection1Content.addListSemantics(7)
        binding.cardTextAlternatives.applyNavigation(R.id.action_homeFragment_to_textAlternativesFragment)
        binding.cardInputFieldLabels.applyNavigation(R.id.action_homeFragment_to_inputFieldLabelsFragment)
        binding.cardFocusableControls.applyNavigation(R.id.action_homeFragment_to_focusableControlsFragment)
        binding.cardTextResizing.applyNavigation(R.id.action_homeFragment_to_textResizingFragment)
        binding.cardTargetSize.applyNavigation(R.id.action_homeFragment_to_targetSizeFragment)
        binding.cardOrientation.applyNavigation(R.id.action_homeFragment_to_orientationFragment)
        binding.cardDarkTheme.applyNavigation(R.id.action_homeFragment_to_darkThemeFragment)

        binding.linearLayoutSection2ExpandCollapseHeader.setAsAccessibilityHeading()
        binding.linearLayoutSection2ExpandCollapseHeader.setOnClickListener { viewModel.toggleSection(SectionId.Section2) }
        addAccessibilityActions(binding.linearLayoutSection2ExpandCollapseHeader, SectionId.Section2, viewModel, sectionExpanded = false)
        binding.linearLayoutSection2Content.addListSemantics(8)
        binding.cardTapTargetGrouping.applyNavigation(R.id.action_homeFragment_to_tapTargetGroupingFragment)
        binding.cardContentGrouping.applyNavigation(R.id.action_homeFragment_to_contentGroupingFragment)
        binding.cardContentGroupReplacement.applyNavigation(R.id.action_homeFragment_to_contentGroupReplacementFragment)
        binding.cardHeadingSemantics.applyNavigation(R.id.action_homeFragment_to_headingSemanticsFragment)
        binding.cardListSemantics.applyNavigation(R.id.action_homeFragment_to_listSemanticsFragment)
        binding.cardAccessibilityReadingOrder.applyNavigation(R.id.action_homeFragment_to_accessibilityReadingOrderFragment)
        binding.cardKeyboardFocusOrder.applyNavigation(R.id.action_homeFragment_to_keyboardFocusOrderFragment)
        binding.cardArrowKeyFocusOrder.applyNavigation(R.id.action_homeFragment_to_arrowKeyFocusOrderFragment)

        binding.linearLayoutSection3ExpandCollapseHeader.setAsAccessibilityHeading()
        binding.linearLayoutSection3ExpandCollapseHeader.setOnClickListener { viewModel.toggleSection(SectionId.Section3) }
        addAccessibilityActions(binding.linearLayoutSection3ExpandCollapseHeader, SectionId.Section3, viewModel, sectionExpanded = false)
        binding.linearLayoutSection3Content.addListSemantics(8)
        binding.cardUxChangeAnnouncements.applyNavigation(R.id.action_homeFragment_to_uxChangeAnnouncementsFragment)
        binding.cardInputErrorAnnouncements.applyNavigation(R.id.action_homeFragment_to_inputErrorAnnouncementsFragment)
        binding.cardAnimationControl.applyNavigation(R.id.action_homeFragment_to_animationControlFragment)
        binding.cardKeyboardType.applyNavigation(R.id.action_homeFragment_to_keyboardTypeFragment)
        binding.cardAccessibilityActionLabels.applyNavigation(R.id.action_homeFragment_to_accessibilityActionLabelsFragment)
        binding.cardCustomStateDescriptions.applyNavigation(R.id.action_homeFragment_to_customStateDescriptionsFragment)
        binding.cardCustomAccessibilityActions.applyNavigation(R.id.action_homeFragment_to_customAccessibilityActionsFragment)
        binding.cardFocusVisibility.applyNavigation(R.id.action_homeFragment_to_focusVisibilityFragment)

        binding.linearLayoutSection4ExpandCollapseHeader.setAsAccessibilityHeading()
        binding.linearLayoutSection4ExpandCollapseHeader.setOnClickListener { viewModel.toggleSection(SectionId.Section4) }
        addAccessibilityActions(binding.linearLayoutSection4ExpandCollapseHeader, SectionId.Section4, viewModel, sectionExpanded = false)
        binding.linearLayoutSection4Content.addListSemantics(6)
        binding.cardAccordion.applyNavigation(R.id.action_homeFragment_to_accordionFragment)
        binding.cardAutocomplete.applyNavigation(R.id.action_homeFragment_to_autocompleteFragment)
        binding.cardAutofill.applyNavigation(R.id.action_homeFragment_to_autofillFragment)
        binding.cardDropdown.applyNavigation(R.id.action_homeFragment_to_dropdownFragment)
        binding.cardInlineLinks.applyNavigation(R.id.action_homeFragment_to_inlineLinksFragment)
        binding.cardLanguageIdentification.applyNavigation(R.id.action_homeFragment_to_languageIdentificationFragment)

        return binding.root
    }

    private fun renderSection(
        isSectionExpanded: Boolean,
        sectionHeader: LinearLayout,
        sectionHeaderImage: ImageView,
        sectionContent: LinearLayout,
        sectionId: SectionId,
        viewModel: HomeViewModel
        ) {
        if (isSectionExpanded) {
            sectionContent.visibility = View.VISIBLE
            sectionHeaderImage.setImageResource(R.drawable.ic_minus_fill)
        } else {
            sectionContent.visibility = View.GONE
            sectionHeaderImage.setImageResource(R.drawable.ic_plus_fill)
        }
        addAccessibilityActions(sectionHeader, sectionId, viewModel, isSectionExpanded)
    }

    private fun renderPage(
        pageModel: HomeScreenState,
        viewModel: HomeViewModel
    ) {
        renderSection(
            pageModel.section1Expanded,
            binding.linearLayoutSection1ExpandCollapseHeader,
            binding.imageSection1ExpandCollapse,
            binding.linearLayoutSection1Content,
            SectionId.Section1,
            viewModel
        )
        renderSection(
            pageModel.section2Expanded,
            binding.linearLayoutSection2ExpandCollapseHeader,
            binding.imageSection2ExpandCollapse,
            binding.linearLayoutSection2Content,
            SectionId.Section2,
            viewModel
        )
        renderSection(
            pageModel.section3Expanded,
            binding.linearLayoutSection3ExpandCollapseHeader,
            binding.imageSection3ExpandCollapse,
            binding.linearLayoutSection3Content,
            SectionId.Section3,
            viewModel
        )
        renderSection(
            pageModel.section4Expanded,
            binding.linearLayoutSection4ExpandCollapseHeader,
            binding.imageSection4ExpandCollapse,
            binding.linearLayoutSection4Content,
            SectionId.Section4,
            viewModel
        )
    }

    private fun addAccessibilityActions(
        sectionHeader: LinearLayout,
        @Suppress("SameParameterValue") sectionId: SectionId,
        viewModel: HomeViewModel,
        sectionExpanded: Boolean
    ) {
        sectionHeader.accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfo
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                if (sectionExpanded) {
                    info.removeAction(
                        AccessibilityNodeInfo.AccessibilityAction(
                            AccessibilityNodeInfo.ACTION_EXPAND,
                            null
                        )
                    )
                    info.addAction(
                        AccessibilityNodeInfo.AccessibilityAction(
                            AccessibilityNodeInfo.ACTION_COLLAPSE,
                            null
                        )
                    )
                } else {
                    info.removeAction(
                        AccessibilityNodeInfo.AccessibilityAction(
                            AccessibilityNodeInfo.ACTION_COLLAPSE,
                            null
                        )
                    )
                    info.addAction(
                        AccessibilityNodeInfo.AccessibilityAction(
                            AccessibilityNodeInfo.ACTION_EXPAND,
                            null
                        )
                    )
                }
            }

            override fun performAccessibilityAction(
                host: View,
                action: Int,
                args: Bundle?
            ): Boolean = when (action) {
                AccessibilityNodeInfo.ACTION_EXPAND,
                AccessibilityNodeInfo.ACTION_COLLAPSE -> {
                    viewModel.toggleSection(sectionId)
                    true
                }
                else -> super.performAccessibilityAction(host, action, args)
            }
        }
    }

    private fun View.applyNavigation(@IdRes navigationId: Int) {
        setOnClickListener(
            Navigation.createNavigateOnClickListener(navigationId, null)
        )
        accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfo
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.addAction(
                    AccessibilityAction(
                        AccessibilityAction.ACTION_CLICK.getId(),
                        getString(R.string.home_show_details)
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}