package io.github.oiwane.alarmsample.widget.listView

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.data.AlarmList
import io.github.oiwane.alarmsample.fileManager.JsonFileManager

class ListViewUpdateManager {
    companion object {
        /**
         * PopupMenuで選択した更新方法でListViewを更新する
         * @param popupMenuTitle PopupMenuの選択したタイトル
         * @param context コンテキスト
         * @param alarmList アラーム情報リスト
         * @param propertyId アラーム情報リストから削除するアラーム情報のID
         * @return 更新の可否
         */
        fun update(
            popupMenuTitle: CharSequence,
            context: Context,
            alarmList: AlarmList,
            propertyId: Int,
            navController: NavController?
        ): Boolean {
            when (popupMenuTitle) {
                context.getString(R.string.popup_edit) -> {
                    val bundle = Bundle()
                    bundle.putString("EDITED_PROPERTY_ID", propertyId.toString())
                    if (navController == null)
                        return false
                    navController.navigate(R.id.EditFragment, bundle)
                    return true
                }
                context.getString(R.string.popup_remove) -> {
                    val property = alarmList.removeAt(propertyId)
                    if (!JsonFileManager(context).write(alarmList)) {
                        alarmList.add(propertyId, property)
                        return false
                    }
                    return true
                }
                else -> return false
            }
        }
    }
}