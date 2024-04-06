# Minimum Touch Target Size
Touch targets should be at least 48dp by 48dp and _must_ be at least 24dp by 24dp.

These minimum sizes are required to support WCAG [Success Criterion 2.5.5 Target Size](https://www.w3.org/TR/WCAG22/#target-size) (Level AAA) and [Success Criterion 2.5.8 Target Size (Minimum)](https://www.w3.org/TR/WCAG22/#target-size-minimum) (Level AA), and the Material Design Guidelines [Touch targets](https://m2.material.io/design/usability/accessibility.html#layout-and-typography).

There are several techniques for achieving appropriate touch target size on an `ImageButton`; however, all but the last are fragile to changes in the underlying drawable.

* Use a drawable resource of the correct size. 
 
```
<!-- Since the drawable resource is 24dp-by-24dp, this ImageButton
     meets minimum, but not recommended, guidelines. -->
<ImageButton
    android:id="@+id/image_button_example_1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/ic_angle_right_outline"
    android:contentDescription="Next" />
```

* Fix `android:layout_width` and `android:layout_height` to `48dp` -- but only if you know the underlying drawable is that size or smaller.

```
<ImageButton
    android:id="@+id/image_button_example_2"
    android:layout_width="48dp"
    android:layout_height="48dp"
    android:src="@drawable/ic_angle_right_outline"
    android:contentDescription="Next" />
```

* Add `android:padding` to expand a drawable's known size to meet the minimum. 

```
<!-- Since this drawable is a 24dp-by-24dp image, 12dp of padding will
     expand the button appropriately to 48dp-by-48dp. -->
<ImageButton
    android:id="@+id/image_button_example_3"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:padding="12dp"
    android:src="@drawable/ic_angle_right_outline"
    android:contentDescription="Next" />
```

* Set `android:minWidth` and `android:minHeight` to `48dp`. (Best practice)

```
<ImageButton
    android:id="@+id/image_button_example_4"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:minWidth="48dp"
    android:minHeight="48dp"
    android:src="@drawable/ic_angle_right_outline"
    android:contentDescription="Next" />
```

Setting `android:minWidth` and `android:minHeight` to `48dp` is by far the preferred method, and applies well to other touchable elements beyond `ImageButton`.

Touch target size can also be increased by appropriately grouping elements; see [Tap Target Grouping](../grouping/TapTargetGrouping.md) for one approach.

(Note: The hard-coded text shown in these examples is only used for simplicity. _Always_ use externalized string resource references in actual code.)

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