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
    private val apiService: ApiService,
    private val appExecutor: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<FavoriteEvent>>>()

    fun getFavoriteEvent(eventId: Int): LiveData<Result<List<FavoriteEvent>>> {
        result.value = Result.Loading
        val client = apiService.getEvents(eventId)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                    if (response.isSuccessful) {
                        val events = response.body()?.listEvents
                        val newList = ArrayList<FavoriteEvent>()
                        appExecutor.diskIO.execute {
                            events?.forEach { event ->
                                val isBookmarked = mFavDao.isEventBookmarked(event.id)
                                val favorite  = FavoriteEvent(
//                                    event.id,
                                    event.name,
                                    event.description,
                                    event.imageLogo,
                                    isBookmarked
                                )
                                newList.add(favorite)
                            }
                            mFavDao.deleteAll()
                            mFavDao.insert(newList)
                        }
                    }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
        val localData = mFavDao.getEvent()
        result.addSource(localData) {newData: List<FavoriteEvent> ->
            result.value = Result.Success(newData)
        }
        return result

    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getDatabase(
            apiService: ApiService,
            mFavDao: FavEventDao,
            appExecutors: AppExecutors
        ) : EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(mFavDao, apiService, appExecutors)
            }.also { instance = it }
    }

}