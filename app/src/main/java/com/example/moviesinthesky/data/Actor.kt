package com.example.moviesinthesky.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Actor(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
)