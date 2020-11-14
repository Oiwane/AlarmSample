package io.github.oiwane.alarmsample.listener

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.PopupMenu
import io.github.oiwane.alarmsample.R

class AlarmListViewOnItemLongClickListener(
    private val activity: Activity,
    private val context: Context
): AdapterView.OnItemLongClickListener{
    override fun onItemLongClick(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ): Boolean {
        if (parent !is ListView)
            return true
        val popup = PopupMenu(context, view!!)
        popup.menuInflater.inflate(R.menu.popup_menut, popup.menu)
        popup.show()
        popup.setOnMenuItemClickListener(PopupMenuOnMenuItemClickListener(activity, context, parent, position))
        return true
    }
}