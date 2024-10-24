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
}
