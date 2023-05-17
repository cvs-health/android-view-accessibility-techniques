# android-view-accessibility-techniques Architecture

## Overall app architecture

android-view-accessibility-techniques is a single-Activity app with separate screens represented by Fragments and orchestrated using the Jetpack Navigation component. 

Screen state is held and controlled by ViewModels and immutable data classes, and propagated to the View-layer Fragments using LiveData classes. In this way, the Model-View-Intent architecture is largely followed, in addition to Model-View-ViewModel.  

There is no significant business logic in the system, so no domain layer classes exists. 

No data is communicated with any back-end system or stored on-device, so the few persistence and data layer classes consist only of hard-coded Kotlin objects. 

All layers for a screen are combined in a single package for that screen.

## File structure

The file tree layout of android-view-accessibility-techniques follows standard Android project structure with package-per-screen organization. All code files relevant to a screen are in the same package: Fragment, ViewModel, etc. 

The key file locations are (in alphabetical tree order):

- app/src
    - androidTest/java/com/cvshealth/accessibility/apps/androidviewaccessibilitytechniques -- contains the generic test runner and all Espresso automation UI test files      
    - main/java/com/cvshealth/accessibility/apps/androidviewaccessibilitytechniques 
        - MainActivity.kt
        - AccessibilityHelper.kt
        - ui -- contains package folders for each screen's code
    - main/res
        - drawable -- contains icons used in the app
        - layout -- contains XML layout files for all fragments, the main activity, all list items, etc.
        - navigation/mobile\_navigation.xml -- the key navigation configuration file
        - values/string.xml -- the key external string file
- doc -- contains Markdown documentation of accessibility techniques and project images

## Adding a new screen

The following process is suggested for adding a new screen to the application. This section lists the key app files you will modify most often and the types of files most used in the app. Other file types, such as specific resource files, are added or edited as appropriate for specific screens. 

1. Add initial screen strings to res/values/strings.xml. In particular, define a menu\_\<screen\_name\> string resource containing the screen title and Home screen button label.
2. Create app/main/res/layout/fragment\_\<screen\_name\>.xml defining the initial screen contents.
3. Create the new app/main/java/com/cvshealth/accessibility/apps/androidviewaccessibilitytechniques/ui/\<screenname\> package folder.
4. In the new package, create the new \<ScreenName\>Fragment.kt code.
5. If necessary, in the same package, create \<ScreenName\>ViewModel.kt to hold state data and/or \<ScreenName\>Model.kt to hold data classes and objects.
6. Revise app/main/res/layout/fragment\_\<screen\_name\>.xml to refer to the correct package and fragment class in tools:context.
7. Edit app/main/res/navigation/mobile\_navigation.xml to (a) add a \<fragment\> linking \<ScreenName\>Fragment to its menu\_\<screen\_name\> string and its layout file, and then (b) add an \<action\> to move from HomeFragment to the new fragment.
8. Edit app/main/res/layout/fragment\_home.xml to copy a card, adjust it to the new screen name, and place it in the appropriate layout location.
9. Add a line to HomeFragment.kt to navigate from the new card to the new fragment using the new navigation activity using the applyNavigation() extension function.
10. Build, run, and test the app.

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