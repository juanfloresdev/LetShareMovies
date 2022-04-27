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
import com.jffp.letsharemovies.enums.ECustonNav

open class ActionFragment : Fragment() {
    lateinit var _eCustomParams: HashMap<String, Any>
    lateinit var _eCustomNavAction: ECustonNav

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            _eCustomParams = requireArguments().getSerializable(HashKeys.NAV_BW_FRAGMENTS) as HashMap<String, Any>
            if (_eCustomParams.containsKey(HashKeys.CUSTOM_NAV_ACTION)){
                _eCustomNavAction = _eCustomParams.get(HashKeys.CUSTOM_NAV_ACTION) as ECustonNav
            }
        }

    }






}