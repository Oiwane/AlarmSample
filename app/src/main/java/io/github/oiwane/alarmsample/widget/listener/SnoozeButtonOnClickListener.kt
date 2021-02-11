package io.github.oiwane.alarmsample.widget.listener

import android.os.PowerManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.alarm.AlarmList
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.message.ErrorMessageToast
import io.github.oiwane.alarmsample.util.Constants

/**
 * スヌーズボタンの押下時の処理
 */
class SnoozeButtonOnClickListener(
    private val activity: AppCompatActivity,
    private val alarmList: AlarmList,
    private val wakeLock: PowerManager.WakeLock
): View.OnClickListener {
    override fun onClick(v: View?) {
        try {
            val requestCodeStr = activity.intent.getStringExtra(Constants.ALARM_REQUEST_CODE) ?: return
            val requestCode = Integer.parseInt(requestCodeStr)
            AlarmConfigurator(activity).snooze(alarmList[requestCode])
        } catch (e: IllegalStateException) {
            Logger.write(LogType.INFO, "bundle don't have key '${Constants.ALARM_REQUEST_CODE}'")
            ErrorMessageToast(activity).showErrorMessage(R.string.error_failed_snooze_alarm)
        } catch (e: NumberFormatException) {
            Logger.write(LogType.ERROR, "failed parse string")
            ErrorMessageToast(activity).showErrorMessage(R.string.error_failed_snooze_alarm)
        } finally {
            if (wakeLock.isHeld)
                wakeLock.release()
            activity.finish()
        }
    }
}