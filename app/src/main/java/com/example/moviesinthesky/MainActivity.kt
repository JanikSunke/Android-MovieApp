package com.example.moviesinthesky

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesinthesky.adapters.MovieListAdapter
import com.example.moviesinthesky.viewModels.MovieViewModel
import com.example.moviesinthesky.viewModels.MovieViewModelFactory
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlin.math.log

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
            override fun onItemClick(position: Int, view: View) {
                val i = Intent(applicationContext, MovieActivity::class.java)
                i.putExtra("id", position + 1)
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