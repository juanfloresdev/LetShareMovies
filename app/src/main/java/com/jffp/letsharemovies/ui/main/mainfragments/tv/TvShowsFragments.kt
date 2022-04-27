package com.jffp.letsharemovies.ui.main.mainfragments.tv

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.ui.main.mainfragments.ActionFragment

class TvShowsFragments : ActionFragment() {

    companion object {
        fun newInstance() = TvShowsFragments()
    }

    private lateinit var viewModel: TvShowsFragmentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_shows_fragments, container, false)
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