# Content Group Replacement
Sometimes content groups should be replaced with a single text that better describes the group for a screen reader user. Applying the `android:contentDescription` property to the enclosing `ViewGroup` layout element performs such replacement. 

```xml
<LinearLayout
    android:id="@+id/rating_section"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:importantForAccessibility="yes"
    android:contentDescription="Rating: 3.4 out of 5 stars" >

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Rating: "/>

    <RatingBar
        android:id="@+id/ratingbar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:numStars="5"
        android:rating="3.4"
        android:isIndicator="true"
        style="@style/Widget.AppCompat.RatingBar.Small" />

    <TextView
        android:id="@+id/rating_value_and_range"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="3.4 / 5"/>
</LinearLayout>
```

If this content were just grouped together, it would be announced poorly by the TalkBack screen reader, sounding something like "Rating: ProgressBar 3.4 5". Replacing the group's `contentDescription` allows the group to be read as "Rating: 3.4 out of 5 stars".

Although the preceding layout snippet uses fixed values for `android:contentDescription` and `android:text`, these values are generally set to dynamic values in code, as follows.

```kotlin
val rating = 3.4f
val maxRating = 5

binding.ratingbar.rating = rating
binding.ratingbar.numStars = maxRating

binding.ratingValueAndRange.text = String.format(
    "%1s / %2s", 
    rating, 
    maxRating)
    
binding.ratingSection.contentDescription = String.format(
    "Rating: %1$s out of %2$s stars",
    rating,
    maxRating)
```

Notes: 

* `getString()` should almost always be used instead of `String.format()`. 

* Apply correct external string pluralization with `<plurals>` in `strings.xml` and `resources.getQuantityString()` in code as appropriate.

(Note: The hard-coded text and fixed data values shown in these examples are only used for simplicity. _Always_ use externalized string resource references and dynamic data providers in actual code.)

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
