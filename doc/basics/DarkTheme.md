# Dark Theme
Support both dark and light themes in accordance with the WAI Mobile Task Force guidance about [supporting the characteristic properties of the platform](https://w3c.github.io/Mobile-A11y-TF-Note/#support-the-characteristic-properties-of-the-platform) (in this case, the "Dark theme" setting) using the following techniques. This will benefit people with a variety of visual disabilities.

In AndroidManifest.xml, set the `<application>` element's `android:theme` property to a style defined in `res/values/themes.xml` based on a Material Design `DayNight` theme and supplying appropriate theme colors for the Light theme. Define the same theme in `res/values-night/themes.xml` with the appropriate theme colors for the Dark theme. 

Be sure both Light theme and Dark theme colors provide sufficient contrast.

See [Dark theme](https://developer.android.com/develop/ui/views/theming/darktheme) for more details.


----

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