package io.github.oiwane.alarmsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.data.AlarmList
import io.github.oiwane.alarmsample.listener.AlarmListViewOnItemLongClickListener

/**
 * アラーム一覧を表示する画面
 */
class ListFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alarmListView: ListView = view.findViewById(R.id.alarmListView)
        val activity = requireActivity()
        val context = requireContext()
        val propertyList: AlarmList = AlarmConfigurator.createPropertyList(context) ?: return
        val list = AlarmConfigurator(activity, context).resetAlarm(propertyList)
        alarmListView.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, list)
        alarmListView.onItemLongClickListener =
            AlarmListViewOnItemLongClickListener(activity, context)
    }
}