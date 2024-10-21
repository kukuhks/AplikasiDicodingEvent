package com.ks.aplikasidicodingevent.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.ks.aplikasidicodingevent.data.repository.EventRepository

class FavoriteViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun getFavoriteEvent(eventId: Int) = eventRepository.getFavoriteEvent(eventId)

}