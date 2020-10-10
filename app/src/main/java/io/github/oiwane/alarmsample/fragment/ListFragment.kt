package io.github.oiwane.alarmsample.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmBroadcastReceiver
import io.github.oiwane.alarmsample.data.AlarmList
import io.github.oiwane.alarmsample.fileManager.JsonFileManager
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.message.ErrorMessageToast
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList

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

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 5)
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmBroadcastReceiver::class.java)
        val requestCode = 0
        val pendingIntent = PendingIntent.getBroadcast(requireActivity(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        intent.data = Uri.parse(requestCode.toString())
        alarmManager.setAlarmClock(AlarmManager.AlarmClockInfo(calendar.timeInMillis, null), pendingIntent)
        Logger.write(LogType.INFO, "set alarm")

        for (property in propertyList)
            list.add(property.title)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
        alarmListView.adapter = adapter
    }
}