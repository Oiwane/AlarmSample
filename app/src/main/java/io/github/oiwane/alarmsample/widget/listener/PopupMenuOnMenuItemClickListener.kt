package io.github.oiwane.alarmsample.widget.listener

import android.app.Activity
import android.content.Context
import android.view.MenuItem
import android.widget.ListView
import android.widget.PopupMenu
import androidx.navigation.NavController
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.message.ErrorMessageToast
import io.github.oiwane.alarmsample.widget.listView.ListViewAdapter
import io.github.oiwane.alarmsample.widget.listView.ListViewUpdateManager

class PopupMenuOnMenuItemClickListener(
    private val activity: Activity,
    private val context: Context,
    private val parent: ListView,
    private val index: Int,
    private val navController: NavController?
): PopupMenu.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem): Boolean {
        val alarmList = AlarmConfigurator.createPropertyList(context)
        if (alarmList == null) {
            ErrorMessageToast(context).showErrorMessage(R.string.error_failed_remove_alarm)
            return true
        }
        ListViewUpdateManager.update(item.title, context, alarmList, alarmList[index].id, navController)
        parent.adapter = ListViewAdapter(activity, context, alarmList)
        return true
    }
}