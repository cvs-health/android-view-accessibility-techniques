# Text Alternatives
All informative non-text content must have a text alternative associated with it, generally via the `contentDescription` property. Providing text alternatives is required by the WCAG 2 [Success Criterion 1.1.1 Non-Text Content](https://www.w3.org/TR/WCAG21/#non-text-content). 

## Informative non-text content

Examples of informative non-text content include `ImageView` elements that convey information and `ImageButton` controls without text. Use a non-empty `android:contentDescription` property value to provide the text alternative.

```
<ImageView
    ...
    android:src="@drawable/ic_sunrise_fill"
    android:contentDescription="Sunrise"
/>
```

```
<ImageButton
    android:id="@+id/image_button_example_6"
    ...
    android:layout_gravity="center_vertical"
    android:src="@drawable/ic_share_fill"
    android:contentDescription="Share" />
```

## Decorative non-text content

Purely decorative content that conveys no meaning must be marked as such with either a null `contentDescription` or `importantForAccessibility="no"`. 

```
<ImageView
    ...
    android:src="@drawable/ic_sprout_fill"
    android:contentDescription="@null"/>
```

```
<ImageView
    ...
    android:src="@drawable/ic_sprout_fill"
    android:importantForAccessibility="no"/>
```

## Avoid redundancy in grouped content

Informative non-text content that is redundant with adjacent text content should be grouped with that text content, rather than given a redundant `contentDescription`. Using `importantForAccessibility="yes"` on the enclosing group layout (and nulling out the non-text content's `contentDescription`) is the simplest way to achieve this.


```
<!-- Good example of grouping content: 
     Enclosing LinearLayout has importantForAccessibility="yes" and
     check mark ImageView has null contentDescription. -->
<LinearLayout
    ...
    android:importantForAccessibility="yes"
    android:orientation="horizontal">
    
    <ImageView
        ...
        android:contentDescription="@null"
        android:src="@drawable/ic_check_fill"
        app:tint="@color/success_green" />
                
    <com.google.android.material.textview.MaterialTextView
        ...
        android:text="Good example of grouping redundant content" />
</LinearLayout>
```

Another alternative for grouping images and text content is to set the `contentDescription` on the enclosing layout group layout. Sometimes this approach is preferable, as is illustrated in the [Content Group Replacement](../grouping/ContentGroupReplacement.md) sample.

## Describing "decorative" images

Although you should never put a non-null `contentDescription` on a purely decorative image, sometimes there are arguments that an apparently decorative image provides tone or emotional content to a screen that should be described for screen reader users. This is a judgement call for the app's designer.

## Handling complex images

Complex images, such as charts and graphs, should have a concise `contentDescription` covering their purpose, but their details also need to be fully presented in the app's text nearby. (If necessary, this text may be on a separate screen that can be easily navigated to using a control near the image). The accessibility of such data visualizations is a separate (and deep) topic.

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