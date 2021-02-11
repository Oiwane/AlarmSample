package io.github.oiwane.alarmsample.widget.listener

import android.os.PowerManager
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
    private val alarmList: AlarmList,
    private val wakeLock: PowerManager.WakeLock
): View.OnClickListener {
    override fun onClick(v: View?) {
        Logger.write(LogType.INFO, "start listener")
        try {
            val requestCode = activity.intent.getStringExtra(Constants.ALARM_REQUEST_CODE)
                ?: throw InvalidAlarmOperationException()
            Logger.write(LogType.INFO, "requestCode : $requestCode")
            val property = alarmList.find {
                requestCode == AlarmConfigurator.requestCodeMap[it.id].toString()
            } ?: throw InvalidAlarmOperationException()
            Logger.write(LogType.INFO, "property : $property")

            if (property.dow.isSpecified())
                AlarmConfigurator(activity).resetAlarm(property)
            else
                AlarmConfigurator(activity).stopAlarm(property)
        } catch (e: IllegalStateException) {
            Logger.write(LogType.INFO, "bundle don't have key '${Constants.ALARM_REQUEST_CODE}'")
        } catch (e: NumberFormatException) {
            Logger.write(LogType.ERROR, "failed parse string")
        } catch (e: InvalidAlarmOperationException) {
            Logger.write(LogType.ERROR, e.message.toString())
        } finally {
            if (wakeLock.isHeld)
                wakeLock.release()
            activity.finish()
            Logger.write(LogType.INFO, "end listener")
        }
    }
}