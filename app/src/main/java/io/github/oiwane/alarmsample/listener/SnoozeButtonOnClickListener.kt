package io.github.oiwane.alarmsample.listener

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.data.AlarmList
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.util.Constants

/**
 * スヌーズボタンの押下時の処理
 */
class SnoozeButtonOnClickListener(
    private val activity: AppCompatActivity,
    private val alarmList: AlarmList
): View.OnClickListener {
    override fun onClick(v: View?) {
        try {
            val requestCodeStr = activity.intent.getStringExtra(Constants.ALARM_REQUEST_CODE) ?: return
            val requestCode = Integer.parseInt(requestCodeStr)
            AlarmConfigurator(activity, activity).snooze(alarmList[requestCode])
            activity.finish()
        } catch (e: IllegalStateException) {
            Logger.write(LogType.INFO, "bundle don't have key '${Constants.ALARM_REQUEST_CODE}'")
        } catch (e: NumberFormatException) {
            Logger.write(LogType.ERROR, "failed parse string")
        }
    }
}