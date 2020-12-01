package io.github.oiwane.alarmsample.listener

import android.app.Activity
import android.content.Context
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.navigation.NavController
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.message.ErrorMessageToast
import io.github.oiwane.alarmsample.widget.listView.ListViewUpdateManager

// TODO : navControllerをactivityから取得するように変更
// TODO : indexはproperty.id: Stringを受け取るようにする
class PopupMenuOnMenuItemClickListener(
    private val activity: Activity,
    private val context: Context,
    private val parent: AdapterView<*>,
    private val index: Int,
    private val navController: NavController?
): PopupMenu.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem): Boolean {
        val alarmList = AlarmConfigurator.createPropertyList(context)
        if (alarmList == null) {
            ErrorMessageToast(context).showErrorMessage(R.string.error_failed_remove_alarm)
            return true
        }
        if (ListViewUpdateManager.update(item.title, context, alarmList, index, navController)) {
            AlarmConfigurator(activity, context).resetAllAlarm(alarmList)
            val list = ArrayList<String>()
            alarmList.forEach { property -> list.add(property.title) }
            parent.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, list)
        }
        return true
    }
}