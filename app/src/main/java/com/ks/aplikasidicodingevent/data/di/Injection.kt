package com.ks.aplikasidicodingevent.data.di

import android.content.Context
import com.ks.aplikasidicodingevent.data.local.room.FavEventRoomDatabase
import com.ks.aplikasidicodingevent.data.repository.EventRepository

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val database = FavEventRoomDatabase.getDatabase(context)
        return EventRepository.getDatabase(database.eventDao())
    }
}