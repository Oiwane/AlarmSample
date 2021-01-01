package io.github.oiwane.alarmsample.widget.listView

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.ToggleButton
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.alarm.AlarmList
import io.github.oiwane.alarmsample.widget.listener.AlarmSettingToggleButtonListener

class ListViewAdapter(
    private val activity: Activity, private val context: Context, private val alarmList: AlarmList
): BaseAdapter() {
    override fun getCount(): Int {
        return alarmList.size
    }

    override fun getItem(position: Int): Any {
        return alarmList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder", "InflateParams", "UseCompatLoadingForDrawables")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_item, null)
        val settingToggleButton: ToggleButton = view.findViewById(R.id.alarmSettingToggleButton)
        val alarmTextView: TextView = view.findViewById(R.id.alarmTextView)

        val alarmProperty = alarmList[position]
        settingToggleButton.isChecked = alarmProperty.isSet
        if (alarmProperty.isSet)
            settingToggleButton.foreground = context.getDrawable(R.drawable.ic_alert_during_setting)
        else
            settingToggleButton.foreground = context.getDrawable(R.drawable.ic_alert_during_stopping)
        alarmTextView.text = alarmProperty.title

        val alarmSettingToggleButtonListener =
            AlarmSettingToggleButtonListener(context, AlarmConfigurator(activity, context), alarmProperty)
        settingToggleButton.setOnCheckedChangeListener(alarmSettingToggleButtonListener)

        return view
    }
}