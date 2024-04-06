# Accessibility Reading Order
Sequence content elements appropriately so they are read by screen readers in an order that preserves the meaning of their visual layout. Incorrectly sequenced content is hard to understand and violates the WCAG [Success Criterion 1.3.2 Meaningful Sequence](https://www.w3.org/TR/WCAG22/#meaningful-sequence). 

There are two techniques for influencing accessibility reading order:

* Correctly sequencing layout elements into groups of related content.
    * For example, to create a sidebar layout in which each column is read completely before the other column is read, the outer layout should have `android:orientation="horizontal"` with two inner vertical layouts, one for each column of text. (If instead the outer layout is vertically-oriented, with inner two-element horizontal layouts, the paragraphs of the main column and the sidebar column will be mixed together in reading order.)
* Apply the property `android:accessibilityTraversalBefore` or `android:accessibilityTraversalAfter` -- but only if necessary. 
    * These properties do not work on all Android platforms and create maintenance issues in layout files, so they should be avoided. Use correct layout sequence and structure instead.
    * `android:accessibilityTraversalBefore` should contain the `android:id` value of the element which will _follow_ the element containing the property. In other words, this property says "My element is _before_ the element with this `id`. (And `android:accessibilityTraversalAfter` declares which element the current element follows.)

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