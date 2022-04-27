package com.jffp.letsharemovies.ui.main.mainfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jffp.letsharemovies.R
import com.jffp.letsharemovies.constants.HashKeys
import com.jffp.letsharemovies.constants.HashKeys.Companion.CUSTOM_NAV_ACTION
import com.jffp.letsharemovies.enums.ECustonNav

class MoviesFragment : Fragment() {

    private lateinit var _eCustomParams: HashMap<String, Any>
    private lateinit var _eCustomNavAction: ECustonNav

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (arguments != null) {
            _eCustomParams = requireArguments().getSerializable(HashKeys.NAV_BW_FRAGMENTS) as HashMap<String, Any>
            if (_eCustomParams.containsKey(CUSTOM_NAV_ACTION)){
                _eCustomNavAction = _eCustomParams.get(CUSTOM_NAV_ACTION) as ECustonNav
            }
        }

        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        val texto = view?.findViewById<TextView>(R.id.texto_ejemplo)
        texto?.text = _eCustomNavAction.catalogType.name
    }


}