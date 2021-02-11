package io.github.oiwane.alarmsample.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.util.LimitedMap
import io.github.oiwane.alarmsample.exception.InvalidAlarmOperationException
import io.github.oiwane.alarmsample.fileManager.JsonFileManager
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.util.Constants
import io.github.oiwane.alarmsample.message.ErrorMessageToast
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class AlarmConfigurator(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(context, AlarmBroadcastReceiver::class.java)

    /**
     * アラームをリセットする
     * @param alarmList アラーム情報リスト
     * @return アラーム一覧に表示する情報
     * @throws InvalidAlarmOperationException アラーム関連の無効な操作をした際に発生
     */
    fun resetAllAlarm(alarmList: AlarmList) {
        alarmList.filter { it.isSet }.forEach { resetAlarm(it) }
    }

    /**
     * アラームをリセットする
     * @param property アラーム情報
     * @throws InvalidAlarmOperationException アラーム関連の無効な操作をした際に発生
     */
    fun resetAlarm(property: AlarmProperty) {
        try {
            stopAlarm(property)
        } catch (e: InvalidAlarmOperationException) {
            Logger.write(LogType.INFO, "unset alarm")
        }
        setUpAlarm(property)
    }

    /**
     * アラームを設定する
     * @param property アラーム情報
     * @throws InvalidAlarmOperationException アラーム関連の無効な操作をした際に発生
     */
    @SuppressLint("SimpleDateFormat")
    fun setUpAlarm(property: AlarmProperty) {
        Logger.write(LogType.INFO, "alarm target : $property")
        val requestCode = IntRange(0, 9).find { !requestCodeMap.values.contains(it) } ?: throw InvalidAlarmOperationException()
        requestCodeMap[property.id] = requestCode
        val calendar = property.calcTriggerCalendar()
        Logger.write(LogType.INFO, SimpleDateFormat(Constants.DATE_FORMAT).format(calendar.time))

        intent.data = Uri.parse(requestCode.toString())
        val pendingIntent = PendingIntent.getBroadcast(
            context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(calendar.timeInMillis, null), pendingIntent
            )
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        property.isSet = true
        Logger.write(LogType.INFO, "set alarm")
    }

    /**
     * アラームを停止する
     * @param property アラーム情報
     * @throws InvalidAlarmOperationException アラーム関連の無効な操作をした際に発生
     */
    fun stopAlarm(property: AlarmProperty) {
        Logger.write(LogType.INFO, "start stopAlarm : ${property.id}")
        requestCodeMap.removeUnusedValue(createPropertyList(context))
        val requestCode = requestCodeMap[property.id]?: throw InvalidAlarmOperationException()
        intent.data = Uri.parse(requestCode.toString())
        val pendingIntent = PendingIntent.getBroadcast(
            context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
        property.isSet = false
        Logger.write(LogType.INFO, "end stopAlarm")
    }

    /**
     * スヌーズ
     * @param property アラーム情報
     * @throws InvalidAlarmOperationException アラーム関連の無効な操作をした際に発生
     */
    fun snooze(property: AlarmProperty) {
        stopAlarm(property)
        val snoozeProperty = AlarmProperty(
            property.id,
            property.title,
            property.hour,
            Calendar.getInstance().get(Calendar.MINUTE) + (property.snoozeTime + 1) * 5,
            property.hasSnoozed,
            property.snoozeTime,
            property.dow,
            true
        )
        setUpAlarm(snoozeProperty)
    }

    companion object {
        val requestCodeMap = LimitedMap(Constants.MAP_MAX_SIZE)

        /**
         * Jsonファイルのアラーム情報をリストにする
         * @param context コンテキスト
         * @return アラーム情報リスト
         */
        fun createPropertyList(context: Context): AlarmList{
            return try {
                var propertyList = JsonFileManager(context).load()

                // ファイルの読み込みができなかった場合
                if (propertyList == null) {
                    ErrorMessageToast(context).showErrorMessage(R.string.error_failed_load_file)
                    propertyList = AlarmList()
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