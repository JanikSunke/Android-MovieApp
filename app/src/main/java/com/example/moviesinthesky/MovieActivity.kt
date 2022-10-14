package com.example.moviesinthesky

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.moviesinthesky.viewModels.MovieViewModel
import com.example.moviesinthesky.viewModels.MovieViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MovieApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movieId = intent.getIntExtra("id", -1)
        if (movieId != -1) {
            lifecycleScope.launch {
                val movie = movieViewModel.get(movieId)
                withContext(Dispatchers.Main) {
                    val movieImageView = findViewById<ImageView>(R.id.imageViewIntent)
                    val src = movie.imageRef?.lowercase()
                    val resourceId = resources.getIdentifier(src?.substring(0, src.indexOf(".")), "drawable", packageName)
                    movieImageView.setImageResource(resourceId)

                    val textViewName = findViewById<TextView>(R.id.textViewIntentName)
                    textViewName.text = movie.title//textViewName.text = movie.title

                    val textViewDirector = findViewById<TextView>(R.id.textViewIntentDirector)
                    textViewDirector.text = movie.director

                    val textViewReleaseYear = findViewById<TextView>(R.id.textViewIntentReleaseYear)
                    textViewReleaseYear.text = movie.releaseYear.toString()
                }
            }
        }


    }

}