package io.github.oiwane.alarmsample.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.listener.SnoozeButtonOnClickListener
import io.github.oiwane.alarmsample.listener.StopButtonOnClickListener
import io.github.oiwane.alarmsample.viewModel.AlarmViewModel

class StopAlarmActivity: AppCompatActivity() {

    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_alarm)

        alarmViewModel = ViewModelProvider(this, AlarmViewModel.Factory(this)).get(AlarmViewModel::class.java)

        val stopButton: Button = findViewById(R.id.stopButton) ?: return
        val snoozeButton: Button = findViewById(R.id.snoozeButton) ?: return

        stopButton.setOnClickListener(StopButtonOnClickListener(this))
        snoozeButton.setOnClickListener(SnoozeButtonOnClickListener(this, alarmViewModel.alarmList.value!!))
    }
}