package com.jffp.letsharemovies.ui.main.fragments.movies.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.constants.HashKeys.Companion.IMAGE_BASE_URL
import com.jffp.letsharemovies.constants.HashKeys.Companion.IMAGE_TRANSITION_PREFIX
import com.jffp.letsharemovies.constants.HashKeys.Companion.MOVIE_BW_TRANSITION
import com.jffp.letsharemovies.model.Movie
import com.jffp.letsharemovies.ui.dialogs.RateMovieDialog
import com.jffp.letsharemovies.ui.dialogs.WatchTrailerDialog

class MovieAdapter(val supportFragmentManager : FragmentManager) :
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
            .inflate(R.layout.fragment_movies_item, parent, false)
        return MovieViewHolder(v, supportFragmentManager)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(itemView: View, supportFragmentManager: FragmentManager) : RecyclerView.ViewHolder(itemView) {

        private val textTitle = itemView.findViewById(R.id.texto_title) as TextView
        private val textDate = itemView.findViewById(R.id.text_release_date) as TextView

        //Image manage
        private val imagePoster = itemView.findViewById(R.id.image) as ImageView

        //Buttons
        private val buttonRate = itemView.findViewById(R.id.button_rate) as Button
        private val buttonWatch = itemView.findViewById(R.id.button_watch) as Button

        fun bind(item: Movie) {
            textTitle.text = item.title
            textDate.text = item.releaseDate
            imagePoster.setTransitionName(IMAGE_TRANSITION_PREFIX + item.id.toString())

            if (item.backdropPath != null) {
                Glide.with(itemView)
                    .load(IMAGE_BASE_URL + item.backdropPath)
                    .placeholder(R.drawable.image_placeholder)
                    .into(imagePoster);
            } else {
                imagePoster.setImageResource(R.drawable.image_error)
            }

            imagePoster.setOnClickListener { view: View? ->
                showDetail(view, item)
            }

            buttonRate.setOnClickListener { view: View? ->
                showRateDialog(view, item)
            }

            buttonWatch.setOnClickListener { view: View? ->
                showTrailerDialog(view, item)
            }

        }

        private fun showTrailerDialog(view: View?, item: Movie) {
            if (view != null) {
                WatchTrailerDialog().show(supportFragmentManager, "RATE_MOVIE_DIALOG")
            }
        }

        private fun showRateDialog(view: View?, item: Movie) {
            if (view != null) {
                RateMovieDialog().show(supportFragmentManager, "RATE_MOVIE_DIALOG")
            }
        }

        private fun showDetail(view: View?, item: Movie) {
            val extras: FragmentNavigator.Extras = FragmentNavigator.Extras.Builder()
                .addSharedElement(imagePoster, IMAGE_TRANSITION_PREFIX + item.id)
                .build()
            val bundle = Bundle()
            bundle.putSerializable(MOVIE_BW_TRANSITION, item)
            findNavController(view!!).navigate(
                R.id.action_moviesFragment_to_movieDetailFragment,
                bundle,
                null,
                extras
            )
            return
        }
    }


}