package com.ks.aplikasidicodingevent.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEvent(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val name: String = "",
    val summary: String = "",
    val imageLogo: String? = null
)
