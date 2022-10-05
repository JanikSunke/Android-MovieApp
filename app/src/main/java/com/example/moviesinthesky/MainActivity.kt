package com.example.moviesinthesky

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesinthesky.adapters.MovieListAdapter
import com.example.moviesinthesky.viewModels.MovieViewModel
import com.example.moviesinthesky.viewModels.MovieViewModelFactory
import androidx.activity.viewModels
import com.example.moviesinthesky.data.AppDatabase


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
        adapter.setOnItemClickListener(object : MovieListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val i = Intent(applicationContext, MovieActivity::class.java)
                i.putExtra("id", position)
                startActivity(i)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        movieViewModel.allMovies.observe(this) {
            movies ->
            movies?.let { adapter.submitList(it) }
        }
    }
}