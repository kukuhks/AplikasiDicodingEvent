package com.ks.aplikasidicodingevent.data.repository

import androidx.lifecycle.LiveData
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.ks.aplikasidicodingevent.data.local.room.FavEventDao

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