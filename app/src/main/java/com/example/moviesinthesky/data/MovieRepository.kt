package com.example.moviesinthesky.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow



class MovieRepository(private val movieDao: MovieDao) {

    val allMovies: Flow<List<Movie>> = movieDao.getAll()

    @WorkerThread
    suspend fun insert(movie: Movie) {
        movieDao.insert(movie)
    }

    @WorkerThread
    suspend fun get(id: Int): Movie {
        return movieDao.get(id);
    }
}