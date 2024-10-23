package com.ks.aplikasidicodingevent.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.ks.aplikasidicodingevent.data.repository.EventRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: EventRepository) : ViewModel() {

    fun getFavoriteStatus() : LiveData<List<FavoriteEvent>> {
        return repository.getAllFavoriteEvent()
    }
}