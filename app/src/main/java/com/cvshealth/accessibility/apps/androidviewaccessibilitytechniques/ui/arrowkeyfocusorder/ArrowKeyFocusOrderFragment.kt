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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.arrowkeyfocusorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentArrowKeyFocusOrderBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with adequate and good examples of keyboard arrow key / direction pad focus order
 * practices. These techniques support the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#focus-order">Success Criterion 2.4.3 Focus Order</a>.
 *
 * See the associated XML layout file, fragment_arrow_key_focus_order.xml, for all important
 * techniques.
 */
class ArrowKeyFocusOrderFragment : Fragment() {

    private var _binding: FragmentArrowKeyFocusOrderBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc and associated XML layout file.

        _binding = FragmentArrowKeyFocusOrderBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        binding.linearLayoutExample1Heading.setAsAccessibilityHeading()
        binding.linearLayoutExample2Heading.setAsAccessibilityHeading()
        binding.imageButtonExample1Left.onClickShowSnackbar(R.string.arrow_key_focus_order_example_move_left_message)
        binding.imageButtonExample1Right.onClickShowSnackbar(R.string.arrow_key_focus_order_example_move_right_message)
        binding.imageButtonExample1Up.onClickShowSnackbar(R.string.arrow_key_focus_order_example_move_up_message)
        binding.imageButtonExample1Down.onClickShowSnackbar(R.string.arrow_key_focus_order_example_move_down_message)
        binding.imageButtonExample2Left.onClickShowSnackbar(R.string.arrow_key_focus_order_example_move_left_message)
        binding.imageButtonExample2Right.onClickShowSnackbar(R.string.arrow_key_focus_order_example_move_right_message)
        binding.imageButtonExample2Up.onClickShowSnackbar(R.string.arrow_key_focus_order_example_move_up_message)
        binding.imageButtonExample2Down.onClickShowSnackbar(R.string.arrow_key_focus_order_example_move_down_message)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Sets an onClickListener on a View that pops up a Snackbar displaying the given message
     * resource string.
     */
    private fun View.onClickShowSnackbar(@StringRes msgId: Int) {
        setOnClickListener {
            val message = getString(msgId)
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        }
    }
}