package com.example.moviesinthesky.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ActorRepository(private val actorDao: ActorDao) {
    val allMovies: Flow<List<Actor>> = actorDao.getAll()

    @WorkerThread
    suspend fun insert(actor: Actor) {
        actorDao.insert(actor)
    }

    @WorkerThread
    suspend fun getFromMovieId(movieId: Int): List<Actor> {
        return actorDao.getFromMovieId(movieId);
    }
}