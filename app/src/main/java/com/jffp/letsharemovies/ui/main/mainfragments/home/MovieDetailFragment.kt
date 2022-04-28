package com.jffp.letsharemovies.ui.main.mainfragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.constants.HashKeys.Companion.IMAGE_BASE_URL
import com.jffp.letsharemovies.constants.HashKeys.Companion.IMAGE_TRANSITION_PREFIX
import com.jffp.letsharemovies.constants.HashKeys.Companion.MOVIE_BW_TRANSITION
import com.jffp.letsharemovies.model.Movie

class MovieDetailFragment : Fragment() {
    private lateinit var _movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            _movie = requireArguments().getSerializable(MOVIE_BW_TRANSITION) as Movie
        }


//        arguments?.let {
//            _movie = it.getSerializable(MOVIE_BW_TRANSITION) as Movie
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.fragment_movie_detail, container, false)

        val imagePoster = view.findViewById<ImageView>(R.id.image_poster)
        imagePoster.setTransitionName(IMAGE_TRANSITION_PREFIX + _movie.id.toString())


        if (_movie?.backdropPath != null) {
            Glide.with(view)
                .load(IMAGE_BASE_URL + _movie!!.backdropPath)
                .placeholder(R.drawable.image_placeholder)
                .into(imagePoster).
                waitForLayout()

        } else {
            imagePoster.setImageResource(R.drawable.image_error)
        }

        sharedElementEnterTransition =
            TransitionInflater.from(this.requireContext())
                .inflateTransition(R.transition.shared_element_container)

        return view
    }

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

}