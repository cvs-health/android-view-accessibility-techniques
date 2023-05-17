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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.accordion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel for holding AccordionFragment's state. No accessibility techniques present here.
 */
class AccordionViewModel : ViewModel() {
    private val _pageModel = MutableLiveData<AccordionStates>()
    val pageModel: LiveData<AccordionStates> = _pageModel

    fun toggleAccordion(accordionId: AccordionId) {
        _pageModel.value = when (accordionId) {
            AccordionId.Section1 ->
                AccordionStates(
                    section1Expanded = !(pageModel.value?.section1Expanded ?: false),
                    section2Expanded = pageModel.value?.section2Expanded ?: false
                )
            AccordionId.Section2 ->
                AccordionStates(
                    section1Expanded = pageModel.value?.section1Expanded ?: false,
                    section2Expanded = !(pageModel.value?.section2Expanded ?: false)
                )
        }
    }
}

enum class AccordionId {
    Section1,
    Section2
}

data class AccordionStates (
    val section1Expanded: Boolean,
    val section2Expanded: Boolean
)
