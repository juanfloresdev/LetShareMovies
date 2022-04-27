package com.jffp.letsharemovies.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("page") @PrimaryKey var page: Int? = null,
    @SerializedName("results") var results: ArrayList<Movie> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null

)