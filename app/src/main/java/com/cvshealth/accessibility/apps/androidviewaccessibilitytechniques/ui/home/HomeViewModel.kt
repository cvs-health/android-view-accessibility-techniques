/*
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
 */
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _pageModel = MutableLiveData<HomeScreenState>()
    val pageModel: LiveData<HomeScreenState> = _pageModel

    fun toggleSection(sectionId: SectionId) {
        _pageModel.value = when (sectionId) {
            SectionId.Section1 ->
                HomeScreenState(
                    section1Expanded = !(pageModel.value?.section1Expanded ?: false),
                    section2Expanded = pageModel.value?.section2Expanded ?: false,
                    section3Expanded = pageModel.value?.section3Expanded ?: false,
                    section4Expanded = pageModel.value?.section4Expanded ?: false
                )
            SectionId.Section2 ->
                HomeScreenState(
                    section1Expanded = pageModel.value?.section1Expanded ?: false,
                    section2Expanded = !(pageModel.value?.section2Expanded ?: false),
                    section3Expanded = pageModel.value?.section3Expanded ?: false,
                    section4Expanded = pageModel.value?.section4Expanded ?: false
                )
            SectionId.Section3 ->
                HomeScreenState(
                    section1Expanded = pageModel.value?.section1Expanded ?: false,
                    section2Expanded = pageModel.value?.section2Expanded ?: false,
                    section3Expanded = !(pageModel.value?.section3Expanded ?: false),
                    section4Expanded = pageModel.value?.section4Expanded ?: false
                )
            SectionId.Section4 ->
                HomeScreenState(
                    section1Expanded = pageModel.value?.section1Expanded ?: false,
                    section2Expanded = pageModel.value?.section2Expanded ?: false,
                    section3Expanded = pageModel.value?.section3Expanded ?: false,
                    section4Expanded = !(pageModel.value?.section4Expanded ?: false)
                )
        }
    }
}

enum class SectionId {
    Section1,
    Section2,
    Section3,
    Section4
}

data class HomeScreenState (
    val section1Expanded: Boolean,
    val section2Expanded: Boolean,
    val section3Expanded: Boolean,
    val section4Expanded: Boolean
)
