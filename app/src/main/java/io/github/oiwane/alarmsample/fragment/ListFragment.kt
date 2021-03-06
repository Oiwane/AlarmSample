package io.github.oiwane.alarmsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.adapter.ListViewAdapter
import io.github.oiwane.alarmsample.listener.AlarmListViewOnItemLongClickListener
import io.github.oiwane.alarmsample.viewModel.AlarmViewModel

/**
 * アラーム一覧を表示する画面
 */
class ListFragment : Fragment() {

    private lateinit var alarmListView: ListView
    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        val context = requireContext()
        alarmViewModel = ViewModelProvider(activity, AlarmViewModel.Factory(context)).get(AlarmViewModel::class.java)

        alarmListView = view.findViewById(R.id.alarmListView)
        val alarmList = alarmViewModel.alarmList.value ?: return
        alarmListView.adapter = ListViewAdapter(activity, context, alarmList)
        alarmListView.onItemLongClickListener =
            AlarmListViewOnItemLongClickListener(activity, context, findNavController())
    }
}