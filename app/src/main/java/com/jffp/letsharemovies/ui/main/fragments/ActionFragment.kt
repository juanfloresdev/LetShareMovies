package com.jffp.letsharemovies.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
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