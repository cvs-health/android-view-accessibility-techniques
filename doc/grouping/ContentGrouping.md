# Content Grouping
Group content elements that should be read together as a single unit by screen readers. Incorrectly grouped content can be misleading and violate the WCAG [Success Criterion 1.3.2 Meaningful Sequence](https://www.w3.org/TR/WCAG22/#meaningful-sequence). And correctly grouping content can significantly improve the audio-textual user experience.

Two factors must be weighed in the successful design of grouped content:

* How long is the combined text?
* How coherent is the text as a block? (In other words, does it all belong together?)

There is a balance to be drawn here between overly long text, which works poorly in a screen reader, and overly short related pieces of text, which will read choppily in a screen reader and can't be skipped over as a unit to get to the next logical grouping. 

Cards and list items are good examples of content that should generally be grouped together and read as a single unit: they are often relatively concise and highly coherent. A screen reader user would also want to skip to the next card or list item with a single swipe, instead of swiping once for each TextView in the card or list item.

Another important use for content grouping is associating pieces information which belongs together, but are presented in different `TextView` elements. Associating the headers and values in simple data table is one example. 

The key techniques for content grouping are:

## Use an enclosing ViewGroup with `android:importantForAccessibility="true"`

To group content, enclose all the relevant content elements in a single layout ViewGroup and set `android:importantForAccessibility="yes"` on that ViewGroup. This property makes the ViewGroup focusable by a screen reader and tells the screen reader to combine all child element text into a single announcement. 

```xml
<!-- All of this card's content will be grouped for accessibility. -->
<com.google.android.material.card.MaterialCardView
    ...
    android:importantForAccessibility="yes" > 
    
    <LinearLayout
        ...
        android:orientation="vertical" >
        <TextView
            android:id="@+id/article_heading"
            ...
            tools:text="Random Article Title"
            style="@style/TextAppearance.MaterialComponents.Headline6" />
        <TextView
            android:id="@+id/article_author"
            ...
            tools:text="Author: Some Random Name" />
        <TextView
            android:id="@+id/article_date"
            ...
            tools:text="Date: 1 Jan 2022" /> 
        <TextView
            android:id="@+id/article_summary"
            ...
            tools:text="This card for a fake article contains random test text designed to illustrate content grouping. Grouped content should be concise and belong together as a unit." />
    </LinearLayout>
    
</com.google.android.material.card.MaterialCardView>

```

## Group content labels with their values

Group any separate content labels with their associated text values. For example, group simple table headings together with their table values. 

* This is an important point: misgrouping or a lack of grouping could read all the table headings, then the table values, without saying what those table values mean. (Which would violate WCAG [Success Criterion 1.3.2 Meaningful Sequence](https://www.w3.org/TR/WCAG22/#meaningful-sequence) and [Success Criterion 1.3.1 Info and Relationships](https://www.w3.org/TR/WCAG22/#info-and-relationships).)

* Note that associating active controls and their labels requires different techniques. (See [Input Field Labels](../basics/InputFieldLabels.md) for details.)

* Complex data tables also require different handling. (See [Text Alternatives](../basics/TextAlternatives.md) for an overview.)

```xml
<!-- This simple table correctly groups "City" with "Boston" 
     and "Population (in 2020)" with "675,647" for accessibility
     services by using layout structure and 
     android:imporantForAccessibility="yes". -->
<LinearLayout
    ...
    android:orientation="horizontal">
    <LinearLayout
        ...
        android:layout_weight="1"
        android:orientation="vertical"
        android:importantForAccessibility="yes" >
        <TextView
            ...
            android:text="City"/>
        <TextView
            ...
            android:text="Boston" />
    </LinearLayout>
    <LinearLayout
        ...
        android:layout_weight="1"
        android:orientation="vertical"
        android:importantForAccessibility="yes" >
        <TextView
            ...
            android:text="Population (in 2020)"/>
        <TextView
            ...
            android:text="675,647" />
    </LinearLayout>
</LinearLayout>

```

## Leverage XML layout sequencing and grouping

Layout `ViewGroup` elements generally sequence their content in strict top-to-bottom, left-to-right order (or right-to-left for RTL languages). So problems of misgrouping can often be addressed by reordering or restructuring the layout elements involved. (Sometimes this involves using simpler `<LinearLayout>` structures instead of `<ConstraintLayout>`.) See [Accessibility Reading Order](./AccessibilityReadingOrder.md) for more information.

## Disable redundant content announcements

Disable announcement of any redundant content within the `ViewGroup` by using `android:importantForAccessibility="no"` or a null `android:contentDescription` (as described in [Text Alternatives](../basics/TextAlternatives.md)).  

Alternatively, override the `contentDescription` of the entire ViewGroup; see [Content Group Replacement](./ContentGroupReplacement.md) for details.

## Apply any `onClickListener` to the appropriate content group

For clickable grouped content (such as cards or list items that allow drilling down to a detailed view), apply an `onClickListener` to the appropriate layout group element and set `android:focusable="true"` (for backward-compatibility with older versions of Android). Doing so will set `android:importantForAccessibility="yes"` implicitly.

Similarly, for apps which support Android versions prior to API level 16, setting `android:focusable="true"` without an `onClickListener` is a hack that enables the same content grouping behavior, but at the cost of improper keyboard focus behavior. Using `android:importantForAccessibility="yes"` is strongly preferred to setting `focusable` without an `onClickListener`.


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
