package com.ks.aplikasidicodingevent.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEvent(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val name: String = "",
    val imageLogo: String? = null,
    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
)