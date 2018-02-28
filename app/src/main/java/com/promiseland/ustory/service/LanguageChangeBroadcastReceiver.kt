package com.promiseland.ustory.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.promiseland.ustory.base.event.SystemLocaleChangedEvent

import org.greenrobot.eventbus.EventBus

class LanguageChangeBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        EventBus.getDefault().post(SystemLocaleChangedEvent())
    }
}
