package io.github.oiwane.alarmsample.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import io.github.oiwane.alarmsample.R

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
        for (i in 1..10)
            list.add(i.toString())
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
        alarmListView.adapter = adapter
    }
}