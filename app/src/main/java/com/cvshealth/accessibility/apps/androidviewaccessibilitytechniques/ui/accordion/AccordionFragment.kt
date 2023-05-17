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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.accordion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.addListItemSemantics
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.addListSemantics
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentAccordionBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

/**
 * Fragment with good and bad examples of creating Accordion control sections that announce their
 * name, role, and value in accordance with the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#name-role-value">Success Criterion 4.1.2 Name, Role, Value</a>
 * by using (or not) the Android standard accessibility actions ACTION_EXPAND (if collapsed) and
 * ACTION_COLLAPSE (if expanded). Only by declaring those accessibility actions is the expanded or
 * collapsed state of an accordion control surfaced to the Android Accessibility API. See
 * addAccessibilityActions() below for implementation details.
 *
 * In both examples, the headings and expanded visual lists incorporate proper heading and list
 * semantics in accordance with the WCAG 2.x success <a href="https://www.w3.org/TR/WCAG21/#info-and-relationships">1.3.1 Info and Relationships</a>
 * by using the extension functions View.setAsAccessibilityHeading(),
 * LinearLayout.addListSemantics(), and View.addListItemSemantics() from AccessibilityHelper.kt.
 *
 * See also the associated XML layout file, fragment_accordion.xml.
 */
class AccordionFragment : Fragment() {

    private var _binding: FragmentAccordionBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc, associated XML layout file, and in-line comments below.

        val viewModel =
            ViewModelProvider(this).get(AccordionViewModel::class.java)

        _binding = FragmentAccordionBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.linearLayoutSection1ExpandCollapseHeader.setAsAccessibilityHeading()
        binding.linearLayoutSection2ExpandCollapseHeader.setAsAccessibilityHeading()

        viewModel.pageModel.observe(viewLifecycleOwner) { pageModel ->
            renderPage(pageModel, viewModel)
        }

        // Apply click handlers to all card buttons; invoke viewModel methods
        binding.linearLayoutSection1ExpandCollapseHeader.setOnClickListener { viewModel.toggleAccordion(AccordionId.Section1) }
        binding.linearLayoutSection2ExpandCollapseHeader.setOnClickListener { viewModel.toggleAccordion(AccordionId.Section2) }
        addAccessibilityActions(binding.linearLayoutSection2ExpandCollapseHeader, AccordionId.Section2, viewModel, sectionExpanded = false)

        // Add list semantics to section 1
        binding.linearLayoutSection1Content.addListSemantics(3)
        binding.textItem11.addListItemSemantics(0)
        binding.textItem12.addListItemSemantics(1)
        binding.textItem13.addListItemSemantics(2)

        // Add list semantics to section 2
        binding.linearLayoutSection2Content.addListSemantics(4)
        binding.textItem21.addListItemSemantics(0)
        binding.textItem22.addListItemSemantics(1)
        binding.textItem23.addListItemSemantics(2)
        binding.textItem24.addListItemSemantics(3)

        return binding.root
    }

    private fun renderPage(
        pageModel: AccordionStates,
        viewModel: AccordionViewModel
    ) {
        // Set accordion content visibility and icons based on state
        if (pageModel.section1Expanded) {
            binding.linearLayoutSection1Content.visibility = View.VISIBLE
            binding.imageSection1ExpandCollapse.setImageResource(R.drawable.ic_minus_fill)
        } else {
            binding.linearLayoutSection1Content.visibility = View.GONE
            binding.imageSection1ExpandCollapse.setImageResource(R.drawable.ic_plus_fill)
        }

        if (pageModel.section2Expanded) {
            binding.linearLayoutSection2Content.visibility = View.VISIBLE
            binding.imageSection2ExpandCollapse.setImageResource(R.drawable.ic_minus_fill)
        } else {
            binding.linearLayoutSection2Content.visibility = View.GONE
            binding.imageSection2ExpandCollapse.setImageResource(R.drawable.ic_plus_fill)
        }
        addAccessibilityActions(binding.linearLayoutSection2ExpandCollapseHeader, AccordionId.Section2, viewModel, pageModel.section2Expanded)
    }

    private fun addAccessibilityActions(
        sectionHeader: LinearLayout,
        @Suppress("SameParameterValue") sectionId: AccordionId,
        viewModel: AccordionViewModel,
        sectionExpanded: Boolean
    ) {
        sectionHeader.accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfo
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                // Key technique: Remove the standard expand or collapse action that does not apply and add the one which does.
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
                // Key technique: Expand or collapse this accordion control based on the action selected.
                AccessibilityNodeInfo.ACTION_EXPAND,
                AccessibilityNodeInfo.ACTION_COLLAPSE -> {
                    viewModel.toggleAccordion(sectionId)
                    true
                }
                else -> super.performAccessibilityAction(host, action, args)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
