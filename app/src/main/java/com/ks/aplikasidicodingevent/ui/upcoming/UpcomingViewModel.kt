package com.ks.aplikasidicodingevent.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ks.aplikasidicodingevent.data.remote.response.EventResponse
import com.ks.aplikasidicodingevent.data.remote.response.ListEventsItem
import com.ks.aplikasidicodingevent.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {
    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val  listEvent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UpcomingViewModel"
//        private const val ACTIVE = 1
    }

    init {
        findEvent()
    }

    private fun findEvent() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(1)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.listEvents?.let { listEventsItems -> _listEvent.value = listEventsItems }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}