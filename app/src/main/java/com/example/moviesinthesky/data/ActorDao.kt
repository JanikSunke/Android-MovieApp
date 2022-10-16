package com.example.moviesinthesky.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ActorDao {
    @Query("SELECT * FROM actor WHERE uid = (:id)")
    suspend fun get(id: Int): Actor

    @Query("SELECT actor.* FROM actor JOIN movie_actor ON movie_actor.actor_id = actor.uid WHERE movie_id = :id")
    suspend fun getFromMovieId(id: Int): List<Actor>

    @Query("SELECT * FROM actor")
    fun getAll(): Flow<List<Actor>>

    @Query("SELECT * FROM actor WHERE name LIKE :name")
    fun findByName(name: String): Movie

    @Query("DELETE FROM actor")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(actor: Actor)

    @Delete
    fun delete(actor: Actor)

    @Query("INSERT INTO movie_actor VALUES (:uid, :movieId, :actorId)")
    suspend fun addToMovie(uid: Int, movieId: Int, actorId: Int)
}