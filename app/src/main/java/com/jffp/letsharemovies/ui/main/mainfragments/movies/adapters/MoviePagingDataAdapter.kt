package com.jffp.letsharemovies.ui.main.mainfragments.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.model.Movie

class MoviePagingDataAdapter:
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? DoggoImageViewHolder)?.bind(item = getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DoggoImageViewHolder.getInstance(parent)
    }

    /**
     * view holder class
     */
    class DoggoImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            //get instance of the DoggoImageViewHolder
            fun getInstance(parent: ViewGroup): DoggoImageViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.fragment_movies_item, parent, false)
                return DoggoImageViewHolder(view)
            }
        }

        var ivDoggoMain: ImageView = view.findViewById(R.id.image)

        fun bind(item: Movie?) {
            //loads image from network using coil extension function
//            ivDoggoMain.load(item) {
//                placeholder(R.drawable.doggo_placeholder)
//            }
        }

    }


}