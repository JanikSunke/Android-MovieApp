package com.example.moviesinthesky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesinthesky.adapters.MovieListAdapter
import com.example.moviesinthesky.viewModels.MovieViewModel
import com.example.moviesinthesky.viewModels.MovieViewModelFactory
import androidx.activity.viewModels
import com.example.moviesinthesky.data.Movie


class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MovieListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        movieViewModel.allMovies.observe(this) {
            movies ->
            movies?.let { adapter.submitList(it) }
        }
    }
}