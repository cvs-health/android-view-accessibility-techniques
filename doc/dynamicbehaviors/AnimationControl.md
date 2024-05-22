# Animation Control
Any screen displaying animations longer than 5 seconds, or which repeat, must provide convenient controls to pause, stop, or hide that content in accordance with the WCAG [Success Criterion 2.2.2 Pause, Stop, Hide](https://www.w3.org/TR/WCAG22/#pause-stop-hide). On Android, that also means supporting the operating system setting "Remove animations" in accordance with the WAI Mobile Task Force guidance regarding [supporting the characteristic properties of the platform](https://w3c.github.io/Mobile-A11y-TF-Note/#support-the-characteristic-properties-of-the-platform).

When "Remove animations" is enabled in a device's Settings app, Android sets the global properties `"transition_animation_scale"`, `"animator_duration_scale"`, and `"window_animation_scale"` to `0f`. Each of these settings corresponds to a specific type of animation.

The following `Context` extension functions make it easier to determine if setting is enabled has been done, and allow disabling each specific type of animation based on its specific global setting:

```kotlin
fun Context.isTransitionAnimationEnabled(): Boolean =
    Settings.Global.getFloat(this.contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 0f) != 0f
fun Context.isAnimatorAnimationEnabled(): Boolean =
    Settings.Global.getFloat(this.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 0f) != 0f
fun Context.isWindowAnimationEnabled(): Boolean =
    Settings.Global.getFloat(this.contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, 0f) != 0f
    
fun Context.isAnimationDisabled(): Boolean =
    !isAnimatorAnimationEnabled() && !isTransitionAnimationEnabled() && !isWindowAnimationEnabled()
```

Also, please note that animations should not cause seizures or physical reactions per WCAG [Guideline 2.3 Seizures and Physical Reactions](https://www.w3.org/TR/WCAG22/#seizures-and-physical-reactions). At Level AA conformance, this means supporting WCAG [Success Criterion 2.3.1 Three Flashes or Below Threshold](https://www.w3.org/TR/WCAG22/#three-flashes-or-below-threshold). Simply put, don't flash any content more than three times per second (or make it very small). No specific coding technique is required to support this criterion, but it may affect animation parameters.

----

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
