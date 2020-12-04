package com.moosetown.voicetobigtext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class UiModel() {

    val text = MutableLiveData("click below to record")

    val textSize = MutableLiveData<Int>(20)

    val editable = MutableLiveData(false)

    val editVisibility = editable.toVisibility()

    val viewOnlyVisibility = editVisibility.inverseVisibility()

    val editButtonText = Transformations.map(editable) { editable ->
        if (editable) {
            "done"
        } else {
            "edit"
        }
    }
}