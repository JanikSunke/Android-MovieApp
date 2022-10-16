package com.example.moviesinthesky.viewModels

import androidx.lifecycle.*
import com.example.moviesinthesky.data.Actor
import com.example.moviesinthesky.data.ActorRepository
import com.example.moviesinthesky.data.Movie
import com.example.moviesinthesky.data.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ActorViewModel(private val repository: ActorRepository) : ViewModel() {

    // Using LiveData and caching what allMovies returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allMovies: LiveData<List<Actor>> = repository.allMovies.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(actor: Actor) = viewModelScope.launch {
        repository.insert(actor)
    }

    /**
     * Launching a new coroutine to get the data in a non-blocking way
     */
    suspend fun getFromMovieId(movieId: Int): List<Actor> {
        return repository.getFromMovieId(movieId)
    }
}

class ActorViewModelFactory(private val repository: ActorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ActorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}