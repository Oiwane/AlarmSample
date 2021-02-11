package io.github.oiwane.alarmsample.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator

class RebootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (!(intent.action == Intent.ACTION_BOOT_COMPLETED ||
                    intent.action == Intent.ACTION_LOCKED_BOOT_COMPLETED))
            return
        Toast.makeText(context, "reset alarm", Toast.LENGTH_LONG).show()
        AlarmConfigurator.createPropertyList(context).filter { it.isSet }.forEach {
            AlarmConfigurator(context).setUpAlarm(it)
        }
    }
}