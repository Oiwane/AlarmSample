package io.github.oiwane.alarmsample.fragment

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
 * A simple [Fragment] subclass as the default destination in the navigation.
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
        val propertyList: AlarmList?
        try {
            propertyList = JsonFileManager(requireContext()).load()

            // ファイルの読み込みができなかった場合
            if (propertyList == null) {
                ErrorMessageToast(requireContext()).showErrorMessage(R.string.error_failed_load_file)
                return
            }
        } catch (e: FileNotFoundException) {
            Logger.write(LogType.ERROR, "not found file.")
            ErrorMessageToast(requireContext()).showErrorMessage(R.string.error_failed_not_found_file)
            return
        }

        val configurator = AlarmConfigurator(requireActivity(), requireContext())
        configurator.resetAllAlarm()
        for ((index, property) in propertyList.withIndex()) {
            list.add(property.title)
            configurator.setUpAlarm(property, index)
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
        alarmListView.adapter = adapter
    }
}