package io.github.oiwane.alarmsample.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.oiwane.alarmsample.data.LimitedMap

class RequestCodeViewModel: ViewModel() {
    class Factory(): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = RequestCodeViewModel() as T
    }

    private val _requestMap = MutableLiveData<LimitedMap>().apply {
        value = LimitedMap(10)
    }
    var requestMap: LiveData<LimitedMap> = _requestMap
}