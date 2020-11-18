package io.github.oiwane.alarmsample.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import io.github.oiwane.alarmsample.activity.StopAlarmActivity
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
        val requestCode = intent!!.data.toString()
        Logger.write(LogType.INFO, "requestCode : $requestCode")
        val intent2 = Intent(context, StopAlarmActivity::class.java)
        intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent2.putExtra("ALARM_REQUEST_CODE", requestCode)
        context!!.startActivity(intent2)
        Logger.write(LogType.INFO, "end")
    }
}