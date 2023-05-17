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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.customstatedescriptions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel for holding CustomStateDescriptionFragment's state. No accessibility techniques present here.
 */
class CustomStateDescriptionsViewModel : ViewModel() {
    private val _customizedCheckboxState = MutableLiveData<Boolean>().apply {
        value = false
    }
    val customizedCheckboxState: LiveData<Boolean> = _customizedCheckboxState

    private val _shieldsState = MutableLiveData<Boolean>().apply {
        value = false
    }
    val shieldsState: LiveData<Boolean> = _shieldsState

    internal fun toggleCustomizedCheckbox() {
        _customizedCheckboxState.value = _customizedCheckboxState.value?.not() ?: true
    }

    internal fun toggleShields() {
        _shieldsState.value = _shieldsState.value?.not() ?: true
    }
}