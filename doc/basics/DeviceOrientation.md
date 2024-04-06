# Device Orientation
Support both portrait and landscape orientations in accordance with the WCAG [Success Criterion 1.3.4 Orientation](https://www.w3.org/TR/WCAG22/#orientation) using the following techniques.

In AndroidManifest.xml, avoid setting any `<activity/>` element's `android:screenOrientation` to a value that fixes the orientation. In other words, avoid `portrait`, `landscape`, etc., in favor of `unspecified` (the default), `sensor`, `fullSensor`, `user`, or `fullUser` orientations. (See the [android:screenOrientation](https://developer.android.com/guide/topics/manifest/activity-element.html#screen) documentation for more details.)

On all full-screen layouts, implement `<ScrollView>` (or a scrolling list  such as `<RecyclerView>`) so the page can adapt to different orientations (as well as different device sizes) without loss of content.

When appropriate, provide alternative layouts for different orientations in `res/layout-land`, `res/layout-port`, and related folders. (See [App resources overview](https://developer.android.com/guide/topics/resources/providing-resources.html) for more details.)

Preserve application state across configurations changes (such as orientation change) using ViewModels or other techniques as described in [Handle configuration changes](https://developer.android.com/guide/topics/resources/runtime-changes).


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