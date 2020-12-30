package io.github.oiwane.alarmsample.listener

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.PopupMenu
import androidx.navigation.NavController
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger

class AlarmListViewOnItemLongClickListener(
    private val context: Context,
    private val navController: NavController?
): AdapterView.OnItemLongClickListener{
    override fun onItemLongClick(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ): Boolean {
        Logger.write(LogType.INFO, "long click ${position}th item")
        if (parent !is ListView)
            return true
        val popup = PopupMenu(context, view!!)
        popup.menuInflater.inflate(R.menu.popup_menut, popup.menu)
        popup.setOnMenuItemClickListener(
            PopupMenuOnMenuItemClickListener(context, position, navController))
        popup.show()
        return true
    }
}