package com.ks.aplikasidicodingevent.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ks.aplikasidicodingevent.data.local.entity.FavoriteEvent

@Database(entities = [FavoriteEvent::class], version = 2, exportSchema = false)
abstract class FavEventRoomDatabase : RoomDatabase() {
    abstract fun eventDao() : FavEventDao

    companion object {
        @Volatile
        private var INSTANCE: FavEventRoomDatabase? = null

        fun getDatabase(context: Context): FavEventRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavEventRoomDatabase::class.java, "Favorite_event"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}