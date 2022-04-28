package com.jffp.letsharemovies

import android.app.Application
import com.jffp.letsharemovies.database.AppDatabase
import com.jffp.letsharemovies.database.LocalInjector

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalInjector.appDatabase = AppDatabase.getDatabase(this@Application)
    }

}