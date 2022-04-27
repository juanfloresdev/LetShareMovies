package com.jffp.letsharemovies.ui.main.mainfragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.constants.HashKeys
import com.jffp.letsharemovies.enums.ECustonNav

class TvShowsFragments : ActionFragment() {

    companion object {
        fun newInstance() = TvShowsFragments()
    }

    private lateinit var viewModel: TvShowsFragmentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tv_shows_fragments_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TvShowsFragmentsViewModel::class.java)

        val texto = view?.findViewById<TextView>(R.id.texto_ejemplo)
        texto?.text = _eCustomNavAction.catalogType.name
    }


}