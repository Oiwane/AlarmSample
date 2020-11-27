package io.github.oiwane.alarmsample.alarm

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.data.AlarmList
import io.github.oiwane.alarmsample.data.AlarmProperty
import io.github.oiwane.alarmsample.fileManager.JsonFileManager
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.message.ErrorMessageToast
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList

class AlarmConfigurator(private val activity: Activity, context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(context, AlarmBroadcastReceiver::class.java)

    /**
     * アラームをリセットする
     * @param alarmList アラーム情報リスト
     * @return アラーム一覧に表示する情報
     */
    fun resetAlarm(alarmList: AlarmList): ArrayList<String> {
        stopAllAlarm()
        return setUpAllAlarm(alarmList)
    }

    /**
     * アラーム情報リストのアラームをセットする
     * @param alarmList アラーム情報リスト
     * @return アラーム一覧に表示する情報
     */
    private fun setUpAllAlarm(alarmList: AlarmList): ArrayList<String> {
        val list = ArrayList<String>()
        for ((index, property) in alarmList.withIndex()) {
            list.add(property.title)
            setUpAlarm(property, index)
        }
        return list
    }

    /**
     * アラームを設定する
     * @param property アラーム情報
     * @param requestCode リクエストコード
     */
    private fun setUpAlarm(property: AlarmProperty, requestCode: Int) {
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
     * アラームをすべて停止する
     */
    private fun stopAllAlarm() {
        Logger.write(LogType.INFO, "start")
        for (requestCode in 0..9) {
            stop(requestCode)
        }
        Logger.write(LogType.INFO, "end")
    }

    /**
     * アラームを停止する
     * @param requestCode アラームのコード
     */
    fun stop(requestCode: Int) {
        Logger.write(LogType.INFO, "start")
        intent.data = Uri.parse(requestCode.toString())
        val pendingIntent = PendingIntent.getBroadcast(
            activity, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
        Logger.write(LogType.INFO, "end")
    }

    /**
     * スヌーズ
     * @param requestCode リクエストコード
     * @param property アラーム情報
     */
    fun snooze(requestCode: Int, property: AlarmProperty) {
        stop(requestCode)
        val snoozeProperty = AlarmProperty(
            property.id,
            property.title,
            property.hour,
            Calendar.getInstance().get(Calendar.MINUTE) + (property.snoozeTime + 1) * 5,
            property.hasSnoozed,
            property.snoozeTime,
            property.dow
        )
        setUpAlarm(snoozeProperty, requestCode)
    }

    companion object {
        const val CHANNEL_ID = "stop_alarm"
        const val ALARM_REQUEST_CODE = "ALARM_REQUEST_CODE"

        /**
         * Jsonファイルのアラーム情報をリストにする
         * @param context コンテキスト
         * @return アラーム情報リスト
         */
        fun createPropertyList(context: Context): AlarmList?{
            return try {
                val propertyList = JsonFileManager(context).load()

                // ファイルの読み込みができなかった場合
                if (propertyList == null) {
                    ErrorMessageToast(context).showErrorMessage(R.string.error_failed_load_file)
                }
                propertyList
            } catch (e: FileNotFoundException) {
                Logger.write(LogType.ERROR, context, R.string.message_not_found_file)
                ErrorMessageToast(context).showErrorMessage(R.string.error_failed_not_found_file)
                AlarmList()
            }
        }
    }
}