package io.github.oiwane.alarmsample.widget.listener

import android.content.Context
import android.widget.CompoundButton
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.alarm.AlarmProperty
import io.github.oiwane.alarmsample.fileManager.JsonFileManager
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.viewModel.AlarmViewModel

class AlarmSettingToggleButtonListener(
    private val activity: FragmentActivity,
    private val context: Context,
    private val alarmProperty: AlarmProperty
): CompoundButton.OnCheckedChangeListener {
    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        try {
            if (isChecked)
                AlarmConfigurator(context).setUpAlarm(alarmProperty)
            else
                AlarmConfigurator(context).stopAlarm(alarmProperty)
            Logger.write(LogType.INFO, "isSet : ${alarmProperty.isSet}")
            val factory = AlarmViewModel.Factory(context)
            val viewModel = ViewModelProvider(activity, factory).get(AlarmViewModel::class.java)
            JsonFileManager(context).write(viewModel.alarmList.value!!)
        } catch (e: Exception) {
            Logger.write(LogType.ERROR, e.message.toString())
        }
    }
}