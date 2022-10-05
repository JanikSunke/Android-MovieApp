package com.example.moviesinthesky

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.example.moviesinthesky.data.Movie
import com.example.moviesinthesky.viewModels.MovieViewModel
import com.example.moviesinthesky.viewModels.MovieViewModelFactory
import kotlinx.coroutines.Job

class MovieActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val id = intent.getIntExtra("id", 0)
        //val movie: Movie = movieViewModel.get(id)

        val textViewName = findViewById<TextView>(R.id.textViewIntentName)
        textViewName.text = "Id: " + id.toString()//textViewName.text = movie.title

        val textViewDirector = findViewById<TextView>(R.id.textViewIntentDirector)
        //textViewDirector.text = movie.director

        val textViewReleaseYear = findViewById<TextView>(R.id.textViewIntentReleaseYear)
        //textViewReleaseYear.text = movie.releaseYear.toString()

    }

}