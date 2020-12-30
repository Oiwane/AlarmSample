package io.github.oiwane.alarmsample.listener

import android.annotation.SuppressLint
import android.content.Context
import android.widget.CompoundButton
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.alarm.AlarmProperty
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger

class AlarmSettingToggleButtonListener(
    private val context: Context,
    private val configurator: AlarmConfigurator,
    private val alarmProperty: AlarmProperty
): CompoundButton.OnCheckedChangeListener {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        try {
            if (isChecked) {
                configurator.setUpAlarm(alarmProperty)
                buttonView.foreground = context.getDrawable(R.drawable.ic_alert_during_setting)
            } else {
                configurator.stopAlarm(alarmProperty.id)
                buttonView.foreground = context.getDrawable(R.drawable.ic_alert_during_stopping)
            }
        } catch (e: Exception) {
            Logger.write(LogType.ERROR, e.message.toString())
        }
    }
}