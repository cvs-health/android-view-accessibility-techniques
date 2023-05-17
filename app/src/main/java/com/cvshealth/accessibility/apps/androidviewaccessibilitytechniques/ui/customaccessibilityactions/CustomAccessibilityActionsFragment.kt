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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.customaccessibilityactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentCustomAccessibilityActionsBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment with examples of bad and good practices of applying custom accessibility actions to
 * improve screen reader user experience.
 *
 * The bad example shown in Example 1 keeps separate clickable elements in a card, resulting in
 * extra swipes to move through the card in the TalkBack screen reader.
 *
 * The key techniques of Examples 2 and 3 are:
 * (1) Marking clickable elements on those cards with android:importantForAccessibility="no" in
 * the associated layout file fragment_custom_accessibility_actions.xml.
 * (2) Declaring custom accessibility action ids in res/values/ids.xml.
 * (3) Adding an AccessibilityDelegate to each card that adds custom accessibility actions with
 * AccessibilityNodeInfo.addAction() and AccessibilityNodeInfo.AccessibilityAction.
 * (4) Add override fun performAccessibilityAction() with a when case to handle each custom
 * accessibility action (or the default action).
 */
class CustomAccessibilityActionsFragment : Fragment() {

    private var _binding: FragmentCustomAccessibilityActionsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc first and in-line comments below second.

        val viewModel =
            ViewModelProvider(this).get(CustomAccessibilityActionsViewModel::class.java)

        _binding = FragmentCustomAccessibilityActionsBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()
        val rootView: View = binding.root

        viewModel.messageEvent.observe(viewLifecycleOwner) { messageEvent ->
            handleMessageEvent(messageEvent, rootView)
        }

        // Apply click handlers to all card buttons; invoke viewModel methods
        binding.card1.setOnClickListener { viewModel.showPostDetails(1) }
        binding.buttonCard1Like.setOnClickListener { viewModel.likePost(1) }
        binding.buttonCard1Share.setOnClickListener { viewModel.sharePost(1) }
        binding.buttonCard1Report.setOnClickListener { viewModel.reportPost(1) }

        binding.card2.setOnClickListener { viewModel.showPostDetails(2) }
        binding.buttonCard2Like.setOnClickListener { viewModel.likePost(2) }
        binding.buttonCard2Share.setOnClickListener { viewModel.sharePost(2) }
        binding.buttonCard2Report.setOnClickListener { viewModel.reportPost(2) }

        binding.card3.setOnClickListener { viewModel.showPostDetails(3) }
        binding.buttonCard3Like.setOnClickListener { viewModel.likePost(3) }
        binding.buttonCard3Share.setOnClickListener { viewModel.sharePost(3) }
        binding.buttonCard3Report.setOnClickListener { viewModel.reportPost(3) }

        // Apply AccessibilityDelegates to cards with custom actions; invoke viewModel methods
        // from performAccessibilityAction() override.
        addCustomAccessibilityActions(binding.card2, 2, viewModel)
        addCustomAccessibilityActions(binding.card3, 3, viewModel)

        return rootView
    }

    private fun handleMessageEvent(
        messageEvent: CustomActionMessageEvent,
        rootView: View
    ) {
        val messageId = when (messageEvent.actionType) {
            CustomActionType.ShowDetails -> R.string.custom_accessibility_actions_show_details_event
            CustomActionType.Like -> R.string.custom_accessibility_actions_like_event
            CustomActionType.Share -> R.string.custom_accessibility_actions_share_event
            CustomActionType.Report -> R.string.custom_accessibility_actions_report_event
        }
        val message = getString(messageId, messageEvent.cardId)
        Snackbar
            .make(rootView, message, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun addCustomAccessibilityActions(
        card: MaterialCardView,
        cardId: Int,
        viewModel: CustomAccessibilityActionsViewModel
    ) {
        card.accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfo
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)

                // Key technique: Add custom accessibility actions. See class-level KDoc for other required steps.
                info.addAction(
                    AccessibilityNodeInfo.AccessibilityAction(R.id.show_details, getString(R.string.custom_accessibility_actions_see_details))
                )
                info.addAction(
                    AccessibilityNodeInfo.AccessibilityAction(R.id.like_post, getString(R.string.custom_accessibility_actions_like))
                )
                info.addAction(
                    AccessibilityNodeInfo.AccessibilityAction(R.id.share_post, getString(R.string.custom_accessibility_actions_share))
                )
                info.addAction(
                    AccessibilityNodeInfo.AccessibilityAction(R.id.report_post, getString(R.string.custom_accessibility_actions_report))
                )
            }

            // Key technique: Handle custom accessibility actions. See class-level KDoc for other required steps.
            override fun performAccessibilityAction(
                host: View,
                action: Int,
                args: Bundle?
            ): Boolean = when (action) {
                R.id.show_details -> {
                    viewModel.showPostDetails(cardId)
                    true
                }
                R.id.like_post -> {
                    viewModel.likePost(cardId)
                    true
                }
                R.id.share_post -> {
                    viewModel.sharePost(cardId)
                    true
                }
                R.id.report_post -> {
                    viewModel.reportPost(cardId)
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