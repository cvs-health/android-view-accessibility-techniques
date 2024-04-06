/*
   Copyright 2023-2024 CVS Health and/or one of its affiliates

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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.autofill

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.autofill.AutofillManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentAutofillBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.tabs.TabLayout

/**
 * Fragment with good examples of applying autofill to input controls in support of the WCAG
 * <a href="https://www.w3.org/TR/WCAG22/#identify-purpose">Success Criterion 1.3.5 Identify Input Purpose</a>
 * and <a href="https://www.w3.org/TR/WCAG22/#redundant-entry">Success Criterion 3.3.7 Redundant Entry</a>.
 *
 * See the associated XML layout file, fragment_autofill.xml, for the key techniques of
 * applying android:inputType and android:autofillHints to input fields. See defaultText() and
 * setTabVisibility() below for one approach to autofilling fields from known app data. See
 * onCreateView() for the Android 8+ technique of directly calling AutofillManager.
 *
 * For more information about supporting autofill, see <a href="https://developer.android.com/guide/topics/text/autofill-optimize">Optimize your app for autofill</a>.
 */
class AutofillFragment : Fragment()  {
    private var _binding: FragmentAutofillBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see the class-level KDoc, the associated XML layout file, and in-line comments below.

        _binding = FragmentAutofillBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        // Example: Shipping and Billing Address Tabs with autofillHints on text input fields
        binding.linearLayoutExample1.setAsAccessibilityHeading()
        binding.linearLayoutExample2.setAsAccessibilityHeading()

        // Key technique: Initialize AutofillManager; see class-level KDoc
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val afm = requireContext().getSystemService(AutofillManager::class.java)
            afm?.requestAutofill(binding.editTextExample1PersonName)
            afm?.requestAutofill(binding.editTextExample1Password)
            afm?.requestAutofill(binding.textInputEditTextExampleShippingPersonName)
            afm?.requestAutofill(binding.textInputEditTextExampleShippingStreetAddress)
            afm?.requestAutofill(binding.textInputEditTextExampleShippingLocality)
            afm?.requestAutofill(binding.textInputEditTextExampleShippingRegion)
            afm?.requestAutofill(binding.textInputEditTextShippingPostCode)
        }

        // Set up tab selection and tab-switching buttons
        binding.tabLayoutExample2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            private fun defaultText(to: AppCompatEditText, from: AppCompatEditText) {
                if (to.text.isNullOrEmpty()) {
                    to.text = from.text
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    setTabVisibility(tab)
                }
            }

            private fun setTabVisibility(tab: TabLayout.Tab) {
                when (tab.position % 2) {
                    0 -> {
                        binding.constraintLayoutExample1BillingAddress.visibility = View.GONE
                        binding.constraintLayoutExample1ShippingAddress.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.constraintLayoutExample1ShippingAddress.visibility = View.GONE

                        // Simplest field defaulting approach: copy the data from field to field.
                        // A more robust approach would use a ViewPager, two Fragments, and a
                        // shared ViewModel.
                        defaultText(
                            binding.textInputEditTextExampleBillingPersonName,
                            binding.textInputEditTextExampleShippingPersonName
                        )
                        defaultText(
                            binding.textInputEditTextExampleBillingStreetAddress,
                            binding.textInputEditTextExampleShippingStreetAddress
                        )
                        defaultText(
                            binding.textInputEditTextExampleBillingLocality,
                            binding.textInputEditTextExampleShippingLocality
                        )
                        defaultText(
                            binding.textInputEditTextExampleBillingRegion,
                            binding.textInputEditTextExampleShippingRegion
                        )
                        defaultText(
                            binding.textInputEditTextBillingPostCode,
                            binding.textInputEditTextShippingPostCode
                        )

                        // Key technique: Update AutoFillManager; see class-level KDoc
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val afm = requireContext().getSystemService(AutofillManager::class.java)
                            afm?.requestAutofill(binding.textInputEditTextExampleBillingPersonName)
                            afm?.requestAutofill(binding.textInputEditTextExampleBillingStreetAddress)
                            afm?.requestAutofill(binding.textInputEditTextExampleBillingLocality)
                            afm?.requestAutofill(binding.textInputEditTextExampleBillingRegion)
                            afm?.requestAutofill(binding.textInputEditTextBillingPostCode)
                        }
                        binding.constraintLayoutExample1BillingAddress.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // No-op
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // No-op
            }
        })

        binding.buttonNext.setOnClickListener {
            binding.tabLayoutExample2.selectTab(binding.tabLayoutExample2.getTabAt(1))
        }

        binding.buttonPrevious.setOnClickListener {
            binding.tabLayoutExample2.selectTab(binding.tabLayoutExample2.getTabAt(0))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}