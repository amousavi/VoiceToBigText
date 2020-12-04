package com.moosetown.voicetobigtext

import android.util.TypedValue
import android.view.View

import android.widget.TextView

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations


@BindingAdapter("textSize")
fun TextView.bindTextSize(size: Int) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
}

fun LiveData<Boolean>.toVisibility(): LiveData<Int> {
    return Transformations.map(this) { booleanLiveDataValue ->
        if (booleanLiveDataValue) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

fun LiveData<Int>.inverseVisibility(): LiveData<Int> {
    return Transformations.map(this) { visibilityIntValue ->
        if (visibilityIntValue == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}