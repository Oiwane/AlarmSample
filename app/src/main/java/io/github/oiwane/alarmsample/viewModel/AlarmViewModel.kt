package io.github.oiwane.alarmsample.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.alarm.AlarmList

class AlarmViewModel(context: Context): ViewModel() {
    class Factory(
        private val context: Context
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = AlarmViewModel(context) as T
    }

    private val _alarmList = MutableLiveData<AlarmList>().apply {
        value = AlarmConfigurator.createPropertyList(context) ?: AlarmList()
    }
    var alarmList: LiveData<AlarmList> = _alarmList
}