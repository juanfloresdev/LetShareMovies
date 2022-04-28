package com.jffp.letsharemovies

import android.app.Application
import com.jffp.letsharemovies.database.AppDatabase
import com.jffp.letsharemovies.database.DatabaseInjector

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseInjector.appDatabase = AppDatabase.getDatabase(this@MoviesApplication)
    }

}