package io.github.oiwane.alarmsample.alarm

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger

/**
 * AlarmManagerからの通知を受け取る
 */
class AlarmBroadcastReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.write(LogType.INFO, "start")
        Toast.makeText(context, "Received", Toast.LENGTH_LONG).show()
        val requestCode = Integer.parseInt(intent!!.data.toString())
        Logger.write(LogType.INFO, "requestCode : $requestCode")
        val activity = context!!.applicationContext as Activity
        AlarmConfigurator(activity, activity).reset(requestCode)
        Logger.write(LogType.INFO, "end")
    }
}