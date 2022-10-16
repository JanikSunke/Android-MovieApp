package com.example.moviesinthesky.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Movie_Actor (
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "movie_id") val movieId: String?,
        @ColumnInfo(name = "actor_id") val actorId: Int?,
)