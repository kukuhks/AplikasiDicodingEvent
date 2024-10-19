package com.ks.aplikasidicodingevent.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ks.aplikasidicodingevent.data.response.EventResponse
import com.ks.aplikasidicodingevent.data.response.ListEventsItem
import com.ks.aplikasidicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {
    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FinishedViewModel"
        private const val ACTIVE = 0
    }

    init {
        findEvent()
    }

    private fun findEvent() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(ACTIVE)
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