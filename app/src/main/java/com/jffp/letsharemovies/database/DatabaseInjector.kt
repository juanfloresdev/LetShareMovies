package com.jffp.letsharemovies.database

object DatabaseInjector {

    var appDatabase: AppDatabase? = null

    fun injectDb(): AppDatabase? {
        return appDatabase
    }
}