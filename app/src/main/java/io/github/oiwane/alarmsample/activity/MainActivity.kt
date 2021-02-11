package io.github.oiwane.alarmsample.activity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.broadcast.ScreenStatusBroadcastReceiver
import io.github.oiwane.alarmsample.viewModel.AlarmViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val receiver = ScreenStatusBroadcastReceiver()
        registerReceiver(receiver, IntentFilter(Intent.ACTION_SCREEN_ON))
        registerReceiver(receiver, IntentFilter(Intent.ACTION_SCREEN_OFF))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.EditFragment)
        }

        val factory = AlarmViewModel.Factory(this)
        val viewModel = ViewModelProvider(this, factory).get(AlarmViewModel::class.java)
        AlarmConfigurator(this).resetAllAlarm(viewModel.alarmList.value!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}