package io.github.oiwane.alarmsample.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.oiwane.alarmsample.util.ScreenStatus

class BackgroundBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_ON)
            ScreenStatus.TurnOn = true
        else if (intent.action == Intent.ACTION_SCREEN_OFF)
            ScreenStatus.TurnOn = false
    }
}