package io.github.oiwane.alarmsample.activity

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.viewModel.AlarmViewModel
import io.github.oiwane.alarmsample.widget.listener.SnoozeButtonOnClickListener
import io.github.oiwane.alarmsample.widget.listener.StopButtonOnClickListener

class StopAlarmActivity: AppCompatActivity() {

    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_alarm)

        alarmViewModel = ViewModelProvider(this, AlarmViewModel.Factory(this)).get(AlarmViewModel::class.java)

        val stopButton: Button = findViewById(R.id.stopButton) ?: return
        val snoozeButton: Button = findViewById(R.id.snoozeButton) ?: return

//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(
                        PowerManager.FULL_WAKE_LOCK
                        or PowerManager.ACQUIRE_CAUSES_WAKEUP
                        or PowerManager.ON_AFTER_RELEASE, "MyApp::MyWakelockTag"
            ).apply {
                acquire()
            }
        }

        val alarmList = alarmViewModel.alarmList.value!!
        stopButton.setOnClickListener(StopButtonOnClickListener(this, alarmList, wakeLock))
        snoozeButton.setOnClickListener(SnoozeButtonOnClickListener(this, alarmList, wakeLock))
    }
}