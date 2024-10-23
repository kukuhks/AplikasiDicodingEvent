package com.ks.aplikasidicodingevent.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.ks.aplikasidicodingevent.data.local.room.FavEventDao
import com.ks.aplikasidicodingevent.data.remote.response.EventResponse
import com.ks.aplikasidicodingevent.data.remote.retrofit.ApiService
import com.ks.aplikasidicodingevent.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository private constructor(
    private val mFavDao: FavEventDao,
) {
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent?> {
        return mFavDao.getFavoriteEventById(id)
    }

    fun getAllFavoriteEvent(): LiveData<List<FavoriteEvent>> {
        return mFavDao.getAllFavoriteEvent()
    }

    suspend fun addToFavorite(event: FavoriteEvent) {
        mFavDao.insert(event)
    }

    suspend fun removeFromFavorite(event: FavoriteEvent) {
        mFavDao.delete(event)
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getDatabase(
            mFavDao: FavEventDao
        ) : EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(mFavDao)
            }.also { instance = it }
    }

}