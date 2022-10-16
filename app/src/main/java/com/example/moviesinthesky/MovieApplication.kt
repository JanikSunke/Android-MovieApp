package com.example.moviesinthesky

import android.app.Application
import android.content.Context
import com.example.moviesinthesky.data.ActorRepository
import com.example.moviesinthesky.data.AppDatabase
import com.example.moviesinthesky.data.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MovieApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MovieRepository(database.movieDao()) }
    val actorRepository by lazy { ActorRepository(database.actorDao()) }
}