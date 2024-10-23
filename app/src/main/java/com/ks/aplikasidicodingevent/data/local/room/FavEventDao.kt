package com.ks.aplikasidicodingevent.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent

@Dao
interface FavEventDao {
//    @Query("SELECT * FROM favorite WHERE bookmarked = 1")
//    fun getBookmarkedEvent():LiveData<List<FavoriteEvent>>
//
//    @Query("select * from favorite order by id asc")
//    fun getEvent(): LiveData<List<FavoriteEvent>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoriteEvent)

    @Update
    suspend fun update(favorite: FavoriteEvent)

    @Delete
    suspend fun delete(favorite: FavoriteEvent)

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent?>

    @Query("SELECT * FROM favorite")
    fun getAllFavoriteEvent(): LiveData<List<FavoriteEvent>>

//    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE id = :id AND bookmarked = 1)")
//    fun isEventBookmarked(id: Int): Boolean
}
