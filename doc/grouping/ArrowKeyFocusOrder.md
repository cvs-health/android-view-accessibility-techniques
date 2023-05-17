# Arrow Key Focus Order
Sequence all focusable active control elements appropriately so they are activated by the keyboard arrow keys (or a D-pad) in an order that preserves the meaning of their visual layout. Incorrect D-pad focus sequences are hard to operate or understand and violate the WCAG 2 [Success Criterion 2.4.3 Focus Order](https://www.w3.org/TR/WCAG21/#focus-order).

Generally, arrow keys correctly select the next control in their direction. However, some layouts require special handling in order to achieve the desired focus order.

The following properties control arrow key focus order:

* `android:nextFocusUp` -- sets the id of the element to go to when the "Up" arrow key is pressed.
* `android:nextFocusDown` -- sets the id of the element to go to when the "Down" arrow key is pressed.
* `android:nextFocusLeft` -- sets the id of the element to go to when the "Left" arrow key is pressed.
* `android:nextFocusRight` -- sets the id of the element to go to when the "Right" arrow key is pressed.

```
<androidx.constraintlayout.widget.ConstraintLayout ... >
    
    <ImageButton
        android:id="@+id/image_button_up"
        ...
        android:nextFocusLeft="@+id/image_button_left"
        android:nextFocusRight="@+id/image_button_right"
        android:nextFocusDown="@+id/image_button_down"
        android:src="@drawable/ic_angle_up_fill"
        android:contentDescription="Move up"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <ImageButton
        android:id="@+id/image_button_left"
        ...
        android:nextFocusRight="@+id/image_button_right"
        android:nextFocusUp="@+id/image_button_up"
        android:nextFocusDown="@+id/image_button_down"
        android:src="@drawable/ic_angle_left_fill"
        android:contentDescription="Move left"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/image_button_up" />

    <ImageButton
        android:id="@+id/image_button_right"
        ...
        android:nextFocusLeft="@+id/image_button_left"
        android:nextFocusUp="@+id/image_button_up"
        android:nextFocusDown="@+id/image_button_down"
        android:src="@drawable/ic_angle_right_fill"
        android:contentDescription="Move right"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/image_button_up" />

    <ImageButton
        android:id="@+id/image_button_down"
        ...
        android:nextFocusLeft="@+id/image_button_left"
        android:nextFocusRight="@+id/image_button_right"
        android:nextFocusUp="@+id/image_button_up"
        android:src="@drawable/ic_angle_down_fill"
        android:contentDescription="Move down"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/image_button_right" />
    
</androidx.constraintlayout.widget.ConstraintLayout>
```

In the example above, the arrow key focus order properties are used to forced focus to move from the "Move up" button to the "Move down" button when the down arrow key is pressed. If this control was not imposed, pressing the down arrow key would move focus to the "Move left" button, since that is the next lower button from "Move up" in the layout.

(Note: The hard-coded text shown in these examples is only used for simplicity. _Always_ use externalized string resource references in actual code.)

----

Copyright 2023 CVS Health and/or one of its affiliates
   
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
[http://www.apache.org/licenses/LICENSE-2.0]()
       
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   
See the License for the specific language governing permissions and
limitations under the License.