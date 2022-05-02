package com.jffp.letsharemovies.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieApiClientInjector {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private var retrofit: Retrofit? = null

    fun getIntance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }


    fun injectApiService(retrofit: Retrofit = getIntance()): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
}