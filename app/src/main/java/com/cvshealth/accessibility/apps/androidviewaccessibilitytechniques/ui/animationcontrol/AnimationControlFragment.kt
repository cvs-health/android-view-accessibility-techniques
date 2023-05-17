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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.animationcontrol

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.R
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.databinding.FragmentAnimationControlBinding
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.isAnimationDisabled
import com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.setAsAccessibilityHeading

const val IS_ANIMATION_PAUSED = "is_animation_paused"

/**
 * Fragment with good examples of animation control practices. These
 * techniques support the WCAG 2.x <a href="https://www.w3.org/TR/WCAG21/#pause-stop-hide">Success Criterion 2.2.2 Pause, Stop, Hide</a>
 * and the WAI Mobile Task Force guidance about
 * <a href="https://w3c.github.io/Mobile-A11y-TF-Note/#support-the-characteristic-properties-of-the-platform">supporting the characteristic properties of the platform</a>
 * (in this case, the "Remove animations" setting).
 *
 * The key techniques are the presence of the Play/Pause button (binding.buttonPlayPaused) and the
 * calls to the Context extension function isAnimationDisabled() defined in AccessibilityHelper.kt.
 */
class AnimationControlFragment : Fragment() {

    private var _binding: FragmentAnimationControlBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var animator: ObjectAnimator? = null
    private var isAnimationPaused = false
    private var animationDuration: Int = 1000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // For key techniques, see class-level KDoc and in-line comments below.

        _binding = FragmentAnimationControlBinding.inflate(inflater, container, false)
        binding.textHeading.setAsAccessibilityHeading()

        try {
            animationDuration = resources.getInteger(android.R.integer.config_longAnimTime)
        } catch (e: Resources.NotFoundException) {
            animationDuration = 1000 // milliseconds
        }

        animator = ObjectAnimator.ofFloat(binding.textFading, "alpha", 0.0f, 1.0f).apply {
            duration = animationDuration.toLong()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }

        isAnimationPaused = savedInstanceState?.getBoolean(IS_ANIMATION_PAUSED) ?: false

        // Key technique: Have a Play/Pause button for any extended or repeating animation.
        binding.buttonPlayPause.setOnClickListener {
            if (isAnimationPaused) {
                animator?.resume()
            } else {
                animator?.pause()
            }
            isAnimationPaused = !isAnimationPaused
            setPlayPauseButtonText()
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_ANIMATION_PAUSED, isAnimationPaused)
    }

    private fun setPlayPauseButtonText() {
        if (isAnimationPaused) {
            binding.buttonPlayPause.text = getString(R.string.animation_control_play)
        } else {
            binding.buttonPlayPause.text = getString(R.string.animation_control_pause)
        }
    }

    override fun onResume() {
        super.onResume()
        // Key technique: Call this extension function to check the "Remove animations" setting. See AccessibilityHelper.kt.
        if (context?.isAnimationDisabled() == true) {
            // Cancelling the animator is not required on recent Android versions, but is on older ones.
            animator?.cancel()
            binding.buttonPlayPause.isEnabled = false
        } else {
            if (animator?.isStarted == false) {
                animator?.start()
            }
            binding.buttonPlayPause.isEnabled = true
            if (isAnimationPaused) {
                animator?.pause()
            }
            setPlayPauseButtonText()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animator?.cancel()
        animator = null
        _binding = null
    }
}