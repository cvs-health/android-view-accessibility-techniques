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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.languageidentification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel to provide a localized language data pair for the Language identification example 3b.
 * No accessibility techniques present here.
 */
class LanguageIdentificationViewModel : ViewModel() {
    private val _localizedLanguageDataState = MutableLiveData<LocaleLanguagePair>()
    val localizedLanguageDataState: LiveData<LocaleLanguagePair> = _localizedLanguageDataState

    fun initStateData() {
        // These state values would normally be retrieved from a data provider.
        // They are hard-coded here only for the sake of simplicity.
        _localizedLanguageDataState.value = LocaleLanguagePair(
            "de",
            "Je mehr sich die Dinge ändern, desto mehr bleiben sie gleich."
        )
    }
}

/**
 * Data class to convey text in a specific language and a String representation of its language
 * locale.
 */
data class LocaleLanguagePair(
    val localeLanguageTag: String,
    val localizedText: CharSequence
)