package com.ks.aplikasidicodingevent.data.di

import android.content.Context
import com.ks.aplikasidicodingevent.data.local.room.FavEventRoomDatabase
import com.ks.aplikasidicodingevent.data.remote.retrofit.ApiConfig
import com.ks.aplikasidicodingevent.data.repository.EventRepository
import com.ks.aplikasidicodingevent.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): EventRepository {
//        val apiService = ApiConfig.getApiService()
        val database = FavEventRoomDatabase.getDatabase(context)
        val dao = database.eventDao()
//        val appExecutors = AppExecutors()
        //      return EventRepository.getDatabase(apiService, dao, appExecutors)
        return EventRepository.getDatabase(database.eventDao())
    }
}