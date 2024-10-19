package com.ks.aplikasidicodingevent.ui.home

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

class HomeViewModel : ViewModel() {
    private val _upcomingListEvent = MutableLiveData<List<ListEventsItem>>()
    val  upcomingListEvent: LiveData<List<ListEventsItem>> get() = _upcomingListEvent

    private val _finishedListEvent = MutableLiveData<List<ListEventsItem>>()
    val  finishedListEvent: LiveData<List<ListEventsItem>> get() = _finishedListEvent

    private val _isLoadingUpcoming = MutableLiveData<Boolean>()
    val isLoadingUpcoming : LiveData<Boolean> = _isLoadingUpcoming

    private val _isLoadingFinished = MutableLiveData<Boolean>()
    val isLoadingFinished : LiveData<Boolean> = _isLoadingFinished

    companion object {
        private const val TAG = "HomeViewModel"
    }

    init {
        findUpcomingEvent()
        findFinishedEvent()
    }

    private fun findUpcomingEvent() {
        _isLoadingUpcoming.value = true
        val client = ApiConfig.getApiService().getEvents(1)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoadingUpcoming.postValue(false)
                if (response.isSuccessful) {
                    response.body()?.listEvents?.let { listEventsItems -> _upcomingListEvent.value = listEventsItems }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingUpcoming.postValue(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun findFinishedEvent() {
        _isLoadingFinished.value = true
        val client = ApiConfig.getApiService().getEvents(0)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoadingFinished.postValue(false)
                if (response.isSuccessful) {
                    response.body()?.listEvents?.let { listEventsItems -> _finishedListEvent.value = listEventsItems }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingFinished.postValue(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}