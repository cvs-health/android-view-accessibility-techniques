/*
   Copyright 2023-2025 CVS Health and/or one of its affiliates

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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

/**
 * Make View a heading for accessibility. This method is required to designate accessibility
 * headings before pre-API 28 (when android:accessibilityHeading became available).
 */
fun View.setAsAccessibilityHeading() {
    ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            info.isHeading = true
        }
    })
}

/**
 * Add accessibility collection semantics to a LinearLayout. Used for manually marking visual lists
 * with list semantics.
 *
 * @param size the size of the list
 */
fun LinearLayout.addListSemantics(size: Int) {
    accessibilityDelegate = object : View.AccessibilityDelegate() {
        override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                info.collectionInfo = AccessibilityNodeInfo.CollectionInfo(size, 1, false)
            } else {
                @Suppress("DEPRECATION")
                info.collectionInfo = AccessibilityNodeInfo.CollectionInfo.obtain(size, 1, false)
            }
        }
    }
}

/**
 * Add accessibility collection item semantics for a layout's child view. Used for manually marking
 * items in visually presented numbered lists.
 *
 * Multiple associated views can share the same index and will be treated semantically as the same
 * list item.
 *
 * @param index zero-based index of this item in the list
 */
fun View.addListItemSemantics(index: Int) {
    accessibilityDelegate = object : View.AccessibilityDelegate() {
        override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                info.collectionItemInfo = AccessibilityNodeInfo.CollectionItemInfo(index, 1, 0, 1, false, false)
            } else {
                @Suppress("DEPRECATION")
                info.collectionItemInfo = AccessibilityNodeInfo.CollectionItemInfo.obtain(index, 1, 0, 1, false, false)
            }
        }
    }
}

// Content extension functions for retrieving system animation control settings.
// Note: Normally, all floating point comparisons should use a threshold value. In this case, it
// does not seem necessary: Android systems settings seem to force these values to a hard zero.

/**
 * Determine if transition animations are enabled.
 */
fun Context.isTransitionAnimationEnabled(): Boolean =
    Settings.Global.getFloat(this.contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 0f) != 0f

/**
 * Determine if Animator framework animations are enabled.
 */
fun Context.isAnimatorAnimationEnabled(): Boolean =
    Settings.Global.getFloat(this.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 0f) != 0f

/**
 * Determine if window animations are enabled.
 */
fun Context.isWindowAnimationEnabled(): Boolean =
    Settings.Global.getFloat(this.contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, 0f) != 0f

/**
 * Determine if the "Disable animations" setting is in effect (or really if any animation type is
 * disabled).
 *
 * While it's generally more appropriate to apply the test specific to the type of animation being
 * employed, this method is useful when the framework is unknown at the point of testing (e.g., due
 * to abstraction layers) or as a shortcut to cover all bases.
 *
 * Note: It may be more correct to apply "&&" instead of "||" here, since "Disable animations"
 * forces all three of the animation scales to zero. I've opted to check if any animation type is
 * disabled instead.
 */
fun Context.isAnimationDisabled(): Boolean =
    !isAnimatorAnimationEnabled() || !isTransitionAnimationEnabled() || !isWindowAnimationEnabled()

/**
 * Add a RadioGroup label to a RadioButton for accessibility.
 *
 * TODO: Generalize to add a group label for any interactive View, not just RadioButton.
 * Note: For Android 16+, refactor this extension function to use info.addLabeledBy and add a function
 * for info.removeLabeledBy (if needed).
 *
 * @param radioGroupLabelView the [View] which labels the [RadioGroup] as a whole
 */
fun RadioButton.setRadioGroupHeading(radioGroupLabelView: View) {
    ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)

            // Key technique: Use setLabeledBy() to associate a group label View with a RadioButton.
            // This is done in addition to setting the RadioButton's field label with android:text.
            info.setLabeledBy(radioGroupLabelView)
        }
    })
}

/**
 * Add a container title to a ViewGroup for accessibility.
 *
 * @param containerTitle the [CharSequence] which labels the [ViewGroup] as a whole
 */
fun ViewGroup.setContainerTitle(containerTitle: CharSequence) {
    ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)

            // Key technique: Use setContainerTitle() to associate a group label with a ViewGroup.
            // This group label will be applied to all of the container's content Views.
            info.containerTitle = containerTitle
        }
    })
}
