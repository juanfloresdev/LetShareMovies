package com.jffp.letsharemovies.enums

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.jffp.letsharemovies.R


enum class ECustonNav(@IdRes val action: Int, val catalogType: ECatalogType) {
    TOP_RATED_MOVIES(R.id.action_homeFragment_to_tvShowsFragments, ECatalogType.TOP_RATED),
    POPULAR_MOVIES(R.id.action_homeFragment_to_moviesFragment, ECatalogType.POPULAR)
}