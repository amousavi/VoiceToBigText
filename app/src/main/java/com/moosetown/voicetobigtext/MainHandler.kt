package com.moosetown.voicetobigtext

import android.view.View

interface MainHandler {

    fun voiceButtonClicked(view: View)

    fun increaseTextSize(view: View)
    fun decreaseTextSize(view: View)

    fun toggleEditability(view: View)
}