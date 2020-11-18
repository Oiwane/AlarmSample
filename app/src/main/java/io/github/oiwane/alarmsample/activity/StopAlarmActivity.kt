package io.github.oiwane.alarmsample.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger

class StopAlarmActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_alarm)

        val stopButton: Button = findViewById(R.id.stopButton) ?: return
        stopButton.setOnClickListener {
            try {
                val requestCodeStr = intent.getStringExtra("ALARM_REQUEST_CODE") ?: return@setOnClickListener
                val requestCode = Integer.parseInt(requestCodeStr)
                AlarmConfigurator(this, this).stop(requestCode)
                finish()
            } catch (e: IllegalStateException) {
                Logger.write(LogType.INFO, "bundle don't have key 'ALARM_REQUEST_CODE'")
            } catch (e: NumberFormatException) {
                Logger.write(LogType.ERROR, "failed parse string")
            }
        }
    }
}