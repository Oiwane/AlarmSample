package io.github.oiwane.alarmsample.alarm

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import io.github.oiwane.alarmsample.data.AlarmProperty
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger

class AlarmConfigurator(private val activity: Activity, context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(context, AlarmBroadcastReceiver::class.java)

    /**
     * アラームを設定する
     * @param property アラーム情報
     * @param requestCode リクエストコード
     */
    fun setUpAlarm(property: AlarmProperty, requestCode: Int) {
        Logger.write(LogType.INFO, "alarm target : $property")
        val calendar = property.calcTriggerCalendar()
        intent.data = Uri.parse(requestCode.toString())
        val pendingIntent = PendingIntent.getBroadcast(
            activity, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(calendar.timeInMillis, null), pendingIntent
            )
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Logger.write(LogType.INFO, "set alarm")
    }

    /**
     * アラームをすべてリセットする
     */
    fun resetAllAlarm() {
        Logger.write(LogType.INFO, "start")
        for (requestCode in 0..9) {
            reset(requestCode)
        }
        Logger.write(LogType.INFO, "end")
    }

    /**
     * アラームをリセットする
     * @param requestCode アラームのコード
     */
    fun reset(requestCode: Int) {
        Logger.write(LogType.INFO, "start")
        intent.data = Uri.parse(requestCode.toString())
        val pendingIntent = PendingIntent.getBroadcast(
            activity, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
        Logger.write(LogType.INFO, "end")
    }
}