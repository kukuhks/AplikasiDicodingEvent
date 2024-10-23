package com.ks.aplikasidicodingevent.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.ks.aplikasidicodingevent.data.remote.response.EventResponse
import com.ks.aplikasidicodingevent.data.remote.response.ListEventsItem
import com.ks.aplikasidicodingevent.data.remote.retrofit.ApiConfig
import com.ks.aplikasidicodingevent.data.repository.EventRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel(private val repository: EventRepository): ViewModel() {
    private var _detailEvent = MutableLiveData<ListEventsItem>()
    val detailEvent: LiveData<ListEventsItem> = _detailEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailEventViewModel"
    }

    fun getDetailEvent(eventId: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(eventId)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.listEvents?.find { it.id == eventId }?.let { detailEvent ->
                        _detailEvent.value = detailEvent
                    }
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


    fun getFavoriteStatus(id: String): LiveData<FavoriteEvent?> {
        return repository.getFavoriteEventById(id)
    }

    fun toggleFavorite(event: FavoriteEvent, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                repository.removeFromFavorite(event)
            } else {
                repository.addToFavorite(event)
            }
        }
    }
}