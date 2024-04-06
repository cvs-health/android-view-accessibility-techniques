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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.autocomplete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentAutoCompleteBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with good examples of creating auto-complete input controls that announce their name,
 * role, and value in accordance with the WCAG <a href="https://www.w3.org/TR/WCAG22/#name-role-value">Success Criterion 4.1.2 Name, Role, Value</a>
 * by using AutocompleteTextView.
 *
 * See the associated XML layout file, fragment_auto_complete.xml, for the pertinent control
 * declarations. See setupExampleAutoComplete() below for the implementation of the data adapters
 * and dropdown list item click listeners.
 */
class AutoCompleteFragment : Fragment()  {
    private var _binding: FragmentAutoCompleteBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // This property is only valid after onCreateView initializes it
    private lateinit var exampleItems: List<CharSequence>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc and associated XML layout file.

        _binding = FragmentAutoCompleteBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        // Example 1: AutoCompleteTextView input control with TextView label
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        setupExampleAutoComplete(
            binding.autoCompleteTextViewExample1,
            R.string.autocomplete_example_1_message
        )

        // Example 2: AutoCompleteTextView inside TextInputLayout
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        setupExampleAutoComplete(
            binding.textInputLayoutExample2.editText as? AutoCompleteTextView,
            R.string.autocomplete_example_2_message
        )

        return binding.root
    }

    /**
     * Set up an AutoCompleteTextView with example data from a string resource array.
     */
    private fun setupExampleAutoComplete(
        autocomplete: AutoCompleteTextView?,
        @StringRes messageId: Int
    ) {
        autocomplete?.apply {

            exampleItems = context.resources.getTextArray(R.array.autocomplete_example_options).asList()
            val autoCompleteAdapter = ArrayAdapter(
                requireContext(),
                R.layout.list_item_dropdown,
                exampleItems
            )
            setAdapter(autoCompleteAdapter)

            validator = object : AutoCompleteTextView.Validator {
                override fun isValid(text: CharSequence?): Boolean {
                    return exampleItems.contains(text.toString())
                }

                override fun fixText(invalidText: CharSequence?): CharSequence {
                    return ""
                }
            }
            onFocusChangeListener = object : OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if (!hasFocus) {
                        (v as? AutoCompleteTextView)?.performValidation()
                    }
                }
            }
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    showSnackbar(
                        getString(
                            messageId,
                            autoCompleteAdapter.getItem(position)
                        )
                    )
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar
            .make(binding.root, message, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}