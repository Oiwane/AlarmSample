package io.github.oiwane.alarmsample.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.viewModel.AlarmViewModel

class StopAlarmActivity: AppCompatActivity() {

    private lateinit var alarmViewMode: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_alarm)

        alarmViewMode = ViewModelProvider(this, AlarmViewModel.Factory(this)).get(AlarmViewModel::class.java)

        val stopButton: Button = findViewById(R.id.stopButton) ?: return
        stopButton.setOnClickListener {
            try {
                val requestCodeStr = intent.getStringExtra(AlarmConfigurator.ALARM_REQUEST_CODE) ?: return@setOnClickListener
                val requestCode = Integer.parseInt(requestCodeStr)
                AlarmConfigurator(this, this).stop(requestCode)
                finish()
            } catch (e: IllegalStateException) {
                Logger.write(LogType.INFO, "bundle don't have key '${AlarmConfigurator.ALARM_REQUEST_CODE}'")
            } catch (e: NumberFormatException) {
                Logger.write(LogType.ERROR, "failed parse string")
            }
        }

        val snoozeButton: Button = findViewById(R.id.snoozeButton) ?: return
        snoozeButton.setOnClickListener {
            try {
                val requestCodeStr = intent.getStringExtra(AlarmConfigurator.ALARM_REQUEST_CODE) ?: return@setOnClickListener
                val requestCode = Integer.parseInt(requestCodeStr)
                AlarmConfigurator(this, this).snooze(requestCode, alarmViewMode.alarmList.value!![requestCode])
                finish()
            } catch (e: IllegalStateException) {
                Logger.write(LogType.INFO, "bundle don't have key '${AlarmConfigurator.ALARM_REQUEST_CODE}'")
            } catch (e: NumberFormatException) {
                Logger.write(LogType.ERROR, "failed parse string")
            }
        }
    }
}