package io.github.oiwane.alarmsample.widget.listView

import android.content.Context
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
         * @param removePropertyId アラーム情報リストから削除するアラーム情報のID
         * @return 更新の可否
         */
        fun update(
            popupMenuTitle: CharSequence, context: Context, alarmList: AlarmList, removePropertyId: Int
        ): Boolean {
            when (popupMenuTitle) {
                context.getString(R.string.popup_edit) -> {
                    // TODO : 編集の処理(編集画面への遷移)
                    return false
                }
                context.getString(R.string.popup_remove) -> {
                    val property = alarmList.removeAt(removePropertyId)
                    if (!JsonFileManager(context).write(alarmList)) {
                        alarmList.add(removePropertyId, property)
                        return false
                    }
                }
                else -> return false
            }
            return true
        }
    }
}