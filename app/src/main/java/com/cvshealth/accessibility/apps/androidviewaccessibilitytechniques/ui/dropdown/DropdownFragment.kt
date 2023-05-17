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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.dropdown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentDropdownBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with good examples of creating dropdown list controls that announce their name, role,
 * and value in accordance with the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#name-role-value">Success Criterion 4.1.2 Name, Role, Value</a>
 * by using the Android standard control Spinner and the Material Design Exposed Dropdown Menu
 * pattern implemented with the controls TextInputLayout and AutocompleteTextView.
 *
 * See the associated XML layout file, fragment_dropdown.xml, for the pertinent control
 * declarations. See below for the implementation of the data adapters and dropdown list item click
 * listeners.
 */
class DropdownFragment : Fragment()  {
    private var _binding: FragmentDropdownBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // This property is only valid after onCreateView initializes it
    private lateinit var exampleItems: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc and associated XML layout file. Mostly, just use
        // the standard dropdown controls.

        _binding = FragmentDropdownBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        exampleItems = listOf(
            getString(R.string.dropdown_example_option_1),
            getString(R.string.dropdown_example_option_2),
            getString(R.string.dropdown_example_option_3),
            getString(R.string.dropdown_example_option_4),
            getString(R.string.dropdown_example_option_5)
        )

        // Example 1: Spinner downdown selector
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            exampleItems
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        // Apply the adapter to the Spinner
        binding.spinnerExample1.adapter = spinnerAdapter
        // Set an item selected listener on the Spinner
        binding.spinnerExample1.onItemSelectedListener = object: OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                showSnackbar(getString(R.string.dropdown_example_1_message, exampleItems[position]))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showSnackbar(getString(R.string.dropdown_example_option_not_selected))
            }
        }

        // Example 2: Exposed Dropdown Menu pattern
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        val autoCompleteAdapter = ArrayAdapter(
            requireContext(),
            R.layout.list_item_dropdown,
            exampleItems
        )
        // Apply the adapter to the AutoCompleteTextView in the TextInputLayout
        (binding.textInputLayoutExample2.editText as? AutoCompleteTextView)?.setAdapter(autoCompleteAdapter)
        // Set an item click listener on the AutoCompleteTextView
        (binding.textInputLayoutExample2.editText as? AutoCompleteTextView)?.onItemClickListener = object: OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                showSnackbar(getString(R.string.dropdown_example_2_message, exampleItems[position]))
            }
        }

        return binding.root
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