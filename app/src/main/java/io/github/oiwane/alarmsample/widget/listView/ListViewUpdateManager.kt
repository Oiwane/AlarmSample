package io.github.oiwane.alarmsample.widget.listView

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmList
import io.github.oiwane.alarmsample.fileManager.JsonFileManager
import io.github.oiwane.alarmsample.util.Constants

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
            propertyId: String,
            navController: NavController?
        ): Boolean {
            return when (popupMenuTitle) {
                context.getString(R.string.popup_edit) -> edit(propertyId, navController)
                context.getString(R.string.popup_remove) -> remove(alarmList, propertyId, context)
                else -> false
            }
        }

        /**
         * アラームを編集する
         * @param propertyId
         */
        private fun edit(propertyId: String, navController: NavController?): Boolean {
            if (navController == null)
                return false
            val bundle = Bundle()
            bundle.putString(Constants.EDITED_PROPERTY_ID, propertyId)
            navController.navigate(R.id.EditFragment, bundle)
            return true
        }

        /**
         * アラームを削除する
         */
        private fun remove(alarmList: AlarmList, propertyId: String, context: Context): Boolean {
            val property = alarmList.findById(propertyId) ?: return false
            val index = alarmList.remove(propertyId)?: return false
            if (!JsonFileManager(context).write(alarmList)) {
                alarmList.add(index, property)
                return false
            }
            return true
        }
    }
}