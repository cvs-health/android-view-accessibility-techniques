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
package com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.ui.customaccessibilityactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel for holding CustomAccessibilityActionsFragment's state. No accessibility techniques present here.
 */
class CustomAccessibilityActionsViewModel : ViewModel() {
    private val _messageEvent = MutableLiveData<CustomActionMessageEvent>()
    val messageEvent: LiveData<CustomActionMessageEvent> = _messageEvent

    fun showPostDetails(cardId: Int) {
        _messageEvent.value = CustomActionMessageEvent(CustomActionType.ShowDetails, cardId)
    }

    fun likePost(cardId: Int) {
        _messageEvent.value = CustomActionMessageEvent(CustomActionType.Like, cardId)
    }

    fun sharePost(cardId: Int) {
        _messageEvent.value = CustomActionMessageEvent(CustomActionType.Share, cardId)
    }

    fun reportPost(cardId: Int) {
        _messageEvent.value = CustomActionMessageEvent(CustomActionType.Report, cardId)
    }
}

enum class CustomActionType {
    ShowDetails,
    Like,
    Share,
    Report
}

data class CustomActionMessageEvent(
    val actionType: CustomActionType,
    val cardId: Int
)