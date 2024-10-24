package com.ks.aplikasidicodingevent.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.ks.aplikasidicodingevent.data.repository.EventRepository

class FavoriteViewModel(private val repository: EventRepository) : ViewModel() {

    fun getFavoriteStatus() : LiveData<List<FavoriteEvent>> {
        return repository.getAllFavoriteEvent()
    }
}