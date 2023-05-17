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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.inputerrorannouncements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel to provide validation for Input Error Announcements' Example 1's form.
 * No accessibility techniques present here.
 */
class InputErrorAnnouncementsViewModel : ViewModel() {
    private val _validationState = MutableLiveData<ValidationResults?>().apply {
        value = null
    }
    val validationState: LiveData<ValidationResults?> = _validationState

    /**
     * Validate the name and password provided and sets the appropriate form state.
     * Only checks that the required fields have non-blank values.
     */
    internal fun validate(name: String, password: String) {
        // If this code actually took time, set _validationState.value to PendingResult.
        if (name.isNotBlank() && password.isNotBlank()) {
            _validationState.value = ValidationResults.SuccessResult()
        } else {
            _validationState.value = ValidationResults.ErrorResult(
                isNameInError = name.isBlank(),
                isPasswordInError = password.isBlank()
            )
        }
    }
}

/**
 * Form validation states.
 */
sealed class ValidationResults {
    class PendingResult : ValidationResults()
    class SuccessResult : ValidationResults()
    data class ErrorResult(
        val isNameInError: Boolean,
        val isPasswordInError: Boolean
    ) : ValidationResults()
}