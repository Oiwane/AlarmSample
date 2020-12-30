package io.github.oiwane.alarmsample.listener

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.alarm.AlarmList
import io.github.oiwane.alarmsample.exception.InvalidAlarmOperationException
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.util.Constants

/**
 * アラーム停止ボタンの押下時の処理
 */
class StopButtonOnClickListener(
    private val activity: AppCompatActivity,
    private val alarmList: AlarmList
): View.OnClickListener {
    override fun onClick(v: View?) {
        try {
            val requestCode = activity.intent.getStringExtra(Constants.ALARM_REQUEST_CODE) ?: throw InvalidAlarmOperationException()
            val property = alarmList.find { requestCode == it.hashCode().toString() } ?: throw InvalidAlarmOperationException()
            if (property.dow.isSpecified())
                AlarmConfigurator(activity, activity).resetAlarm(property)
            else
                AlarmConfigurator(activity, activity).stopAlarm(property.id)
            activity.finish()
        } catch (e: IllegalStateException) {
            Logger.write(LogType.INFO, "bundle don't have key '${Constants.ALARM_REQUEST_CODE}'")
        } catch (e: NumberFormatException) {
            Logger.write(LogType.ERROR, "failed parse string")
        } catch (e: InvalidAlarmOperationException) {
            Logger.write(LogType.ERROR, e.message.toString())
        }
    }
}