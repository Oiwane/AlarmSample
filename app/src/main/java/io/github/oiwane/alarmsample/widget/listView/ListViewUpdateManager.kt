package io.github.oiwane.alarmsample.widget.listView

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.data.AlarmList
import io.github.oiwane.alarmsample.fileManager.JsonFileManager

class ListViewUpdateManager {
    companion object {
        // TODO : propertyIdとしてproperty.idを受け取る
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
            return when (popupMenuTitle) {
                context.getString(R.string.popup_edit) -> {
                    edit(propertyId, navController)
                }
                context.getString(R.string.popup_remove) -> {
                    remove(alarmList, propertyId, context)
                }
                else -> false
            }
        }

        /**
         * アラームを編集する
         * @param propertyId
         */
        private fun edit(propertyId: Int, navController: NavController?): Boolean {
            val bundle = Bundle()
            bundle.putString("EDITED_PROPERTY_ID", propertyId.toString())
            if (navController == null)
                return false
            navController.navigate(R.id.EditFragment, bundle)
            return true
        }

        /**
         * アラームを削除する
         */
        private fun remove(alarmList: AlarmList, propertyId: Int, context: Context): Boolean {
            val property = alarmList.removeAt(propertyId)
            if (!JsonFileManager(context).write(alarmList)) {
                alarmList.add(propertyId, property)
                return false
            }
            return true
        }
    }
}