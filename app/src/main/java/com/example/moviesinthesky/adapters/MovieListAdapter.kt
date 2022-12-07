package com.example.moviesinthesky.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesinthesky.R
import com.example.moviesinthesky.data.Movie

class MovieListAdapter : ListAdapter<Movie, MovieListAdapter.MovieViewHolder?>(MovieComparator()) {
    private lateinit var listener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title, current.imageRef)
    }

    class MovieViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val movieItemView: TextView = itemView.findViewById(R.id.textView)
        private val movieImageView: ImageView = itemView.findViewById(R.id.imageView)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(title: String?, imageRef: String?) {
            movieItemView.text = title
            val src = imageRef?.lowercase()
            val resourceId = itemView.resources.getIdentifier(src?.substring(0, src.indexOf(".")), "drawable", itemView.context.packageName);
            movieImageView.setImageResource(resourceId)
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition, it)
            }
        }

        companion object {
            fun create(parent: ViewGroup, listener: OnItemClickListener): MovieViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return MovieViewHolder(view, listener)
            }
        }

    }
    class MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }
    }
}