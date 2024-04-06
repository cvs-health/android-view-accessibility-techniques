# RatingBar Controls
To create interactive rating bar controls, i.e. "star ratings," that announce their name, role, and value in accordance with the WCAG [Success Criterion 4.1.2 Name, Role, Value](https://www.w3.org/TR/WCAG22/#name-role-value) and work well with assistive technologies, use the standard Android `RatingBar` control. Custom approaches are likely to be less accessible.

In XML, declare the `RatingBar` (in this case, using `AppCompatRatingBar`) and set the following properties:

* `android:id` in order to refer to this control in code.
* `android:layout_width="wrap_content` -- bad things happen if "match_parent" is used.
* `android:numStars` sets the number of maximum rating value allowed.
* `android:stepSize` sets the increment interval; defaults to 0.5, but best to set it explicitly.
* `android:rating` sets the initial rating value.

For example:

```
<androidx.appcompat.widget.AppCompatRatingBar
    android:id="@+id/ratingbar1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:numStars="5"
    android:stepSize="0.25"
    android:rating="0"
    />
```

The appearance of a `RatingBar` can also be controlled using:

* `android:progressTint` - the color of the selected portion of the `RatingBar` icons.
* `android:progressBackgroundTint` - the color of the unselected portion of the `RatingBar` icons.
* `android:progressDrawable` - a drawable, generally a layer list, for the `RatingBar` icons themselves.

For consistency across your app, these properties should be wrapped in a `style` resource, derived from the parent `Widget.AppCompat.RatingBar`.

In code, assign an `OnRatingBarChangeListener` to capture control value changes. 

For example:

```kotlin
binding.ratingbar1.setOnRatingBarChangeListener { ratingBar: RatingBar, rating: Float, fromUser: Boolean ->
    if (fromUser) { 
        // Testing fromUser avoids potential loops when control value is changed programmatically.
        // Update ViewModel or other controls here...
    }
}
```

Also, set any dynamically-determined initial control values, such as a rating from a database or API (not shown).

(Note: The hard-coded text shown in these examples is only used for simplicity. _Always_ use externalized string resource references in actual code.)

----

Copyright 2024 CVS Health and/or one of its affiliates

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

See the License for the specific language governing permissions and
limitations under the License.

