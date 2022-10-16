package com.example.moviesinthesky.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ActorRepository(private val actorDao: ActorDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allMovies: Flow<List<Actor>> = actorDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun insert(actor: Actor) {
        actorDao.insert(actor)
    }

    @WorkerThread
    suspend fun getFromMovieId(movieId: Int): List<Actor> {
        return actorDao.getFromMovieId(movieId);
    }
}