package com.jffp.letsharemovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.model.Movie

class MovieAdapter() :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.movies_fragment_item, parent, false)
        return MovieViewHolder(v)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textTitle = itemView.findViewById(R.id.texto_title) as TextView
        private val textDate = itemView.findViewById(R.id.text_release_date) as TextView

        //Image manage
        private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        private val imagePoster = itemView.findViewById(R.id.image) as ImageView


        fun bind(item: Movie) {
            textTitle.text = item.title
            textDate.text = item.releaseDate

            if (item.backdropPath != null) {
                Glide.with(itemView)
                    .load(IMAGE_BASE_URL + item.backdropPath)
                    .placeholder(R.drawable.image_placeholder)
                    .into(imagePoster);
            } else {
                imagePoster.setImageResource(R.drawable.image_error)
            }


        }
    }


}