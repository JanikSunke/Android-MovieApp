package com.example.moviesinthesky.viewModels

import androidx.lifecycle.*
import com.example.moviesinthesky.data.Movie
import com.example.moviesinthesky.data.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    // Using LiveData and caching what allMovies returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allMovies: LiveData<List<Movie>> = repository.allMovies.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(movie: Movie) = viewModelScope.launch {
        repository.insert(movie)
    }

    /**
     * Launching a new coroutine to get the data in a non-blocking way
     */
    fun get(id: Int) = viewModelScope.launch {
        repository.get(id)
    }

}

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}