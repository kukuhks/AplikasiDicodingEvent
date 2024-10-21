package com.ks.aplikasidicodingevent.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent

@Dao
interface FavEventDao {
    @Query("SELECT * FROM favorite WHERE bookmarked = 1")
    fun getBookmarkedEvent():LiveData<List<FavoriteEvent>>

    @Query("select * from favorite order by id asc")
    fun getEvent(): LiveData<List<FavoriteEvent>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: List<FavoriteEvent>)

    @Update
    fun update(favorite: FavoriteEvent)

    @Query("DELETE FROM favorite WHERE bookmarked = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE id = :id AND bookmarked = 1)")
    fun isEventBookmarked(id: Int): Boolean
}