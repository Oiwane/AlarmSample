package io.github.oiwane.alarmsample.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger

class AlarmBroadcastReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.write(LogType.INFO, "start")
        Toast.makeText(context, "Received", Toast.LENGTH_LONG).show()
        val manager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intentForCancel = Intent(context.applicationContext, AlarmBroadcastReceiver::class.java)
        val requestCode = Integer.parseInt(intent!!.data.toString())
        Logger.write(LogType.INFO, "requestCode : $requestCode")
        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            requestCode,
            intentForCancel,
            PendingIntent.FLAG_CANCEL_CURRENT)
        manager.cancel(pendingIntent)
        Logger.write(LogType.INFO, "end")
    }
}