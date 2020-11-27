package io.github.oiwane.alarmsample.listener

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.PopupMenu
import androidx.navigation.NavController
import io.github.oiwane.alarmsample.R

class AlarmListViewOnItemLongClickListener(
    private val activity: Activity,
    private val context: Context,
    private val navController: NavController?
): AdapterView.OnItemLongClickListener{
    override fun onItemLongClick(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ): Boolean {
        if (parent !is ListView)
            return true
        val popup = PopupMenu(context, view!!)
        popup.menuInflater.inflate(R.menu.popup_menut, popup.menu)
        // TODO positionではなくproperty.idを渡す
        popup.setOnMenuItemClickListener(PopupMenuOnMenuItemClickListener(activity, context, parent, position, navController))
        popup.show()
        return true
    }
}