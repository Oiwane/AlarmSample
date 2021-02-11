package io.github.oiwane.alarmsample.widget.listView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmList
import io.github.oiwane.alarmsample.viewModel.AlarmViewModel
import io.github.oiwane.alarmsample.widget.listener.AlarmSettingToggleButtonListener
import io.github.oiwane.alarmsample.widget.toggleButton.AlarmSettingToggleButton

class ListViewAdapter(
    private val activity: FragmentActivity, private val context: Context
): BaseAdapter() {
    private val alarmList: AlarmList = run {
        val viewModel = ViewModelProvider(activity, AlarmViewModel.Factory(context)).get(AlarmViewModel::class.java)
        viewModel.alarmList.value!!
    }

    override fun getCount(): Int {
        return alarmList.size
    }

    override fun getItem(position: Int): Any {
        return alarmList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_item, null)
        val toggleButton: AlarmSettingToggleButton = view.findViewById(R.id.alarmSettingToggleButton)
        val alarmTextView: TextView = view.findViewById(R.id.alarmTextView)

        val alarmProperty = alarmList[position]
        toggleButton.isChecked = alarmProperty.isSet
        alarmTextView.text = alarmProperty.title

        val listener = AlarmSettingToggleButtonListener(activity, context, alarmProperty)
        toggleButton.setOnCheckedChangeListener(listener)

        return view
    }
}