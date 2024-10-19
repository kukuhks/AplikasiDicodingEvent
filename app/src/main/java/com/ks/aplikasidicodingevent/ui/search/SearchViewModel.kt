package com.ks.aplikasidicodingevent.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ks.aplikasidicodingevent.data.response.EventResponse
import com.ks.aplikasidicodingevent.data.response.ListEventsItem
import com.ks.aplikasidicodingevent.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent: LiveData<List<ListEventsItem>> get() = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

//    companion object {
//        private const val ACTIVE = -1
//    }

    fun findEvent(status: Int, query: String) {
        _isLoading.value = true  // Show loading indicator

        val client = ApiConfig.getApiService().search(status, query)  // Pass -1 and query to the API
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false  // Hide loading indicator

                if (response.isSuccessful) {
                    response.body()?.listEvents?.let { listEventsItems ->
                        _listEvent.value = listEventsItems  // Update the LiveData
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false  // Hide loading indicator on error
            }
        })
    }
}
