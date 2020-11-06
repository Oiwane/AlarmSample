package io.github.oiwane.alarmsample.fragment

import android.content.Context
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
import io.github.oiwane.alarmsample.fileManager.JsonFileManager
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.message.ErrorMessageToast
import java.io.FileNotFoundException

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
        val list = ArrayList<String>()
        val context = requireContext()
        val propertyList: AlarmList = createPropertyList(context) ?: return

        val configurator = AlarmConfigurator(requireActivity(), context)
        configurator.resetAllAlarm()
        for ((index, property) in propertyList.withIndex()) {
            list.add(property.title)
            configurator.setUpAlarm(property, index)
        }
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, list)
        alarmListView.adapter = adapter
    }

    /**
     * Jsonファイルのアラーム情報をリストにする
     * @param context コンテキスト
     * @return アラーム情報リスト
     */
    private fun createPropertyList(context: Context): AlarmList?{
        return try {
            val propertyList = JsonFileManager(context).load()

            // ファイルの読み込みができなかった場合
            if (propertyList == null) {
                ErrorMessageToast(context).showErrorMessage(R.string.error_failed_load_file)
            }
            propertyList
        } catch (e: FileNotFoundException) {
            Logger.write(LogType.ERROR, context, R.string.message_not_found_file)
            ErrorMessageToast(context).showErrorMessage(R.string.error_failed_not_found_file)
            null
        }
    }
}