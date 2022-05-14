package com.jffp.letsharemovies.ui.main.fragments.tv

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.ui.main.fragments.ActionFragment

class TvShowsFragments : ActionFragment() {

    companion object {
        fun newInstance() = TvShowsFragments()
    }

    private lateinit var viewModel: TvShowsFragmentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TvShowsFragmentsViewModel::class.java)

        val title = view?.findViewById<TextView>(R.id.title)
        title?.text = _eCustomNavAction.catalogType.title

        val subTitle = view?.findViewById<TextView>(R.id.sub_title)
        subTitle?.text = _eCustomNavAction.catalogType.subTitle

    }


}