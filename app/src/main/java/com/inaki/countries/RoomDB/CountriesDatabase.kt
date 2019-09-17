package com.inaki.countries.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CountriesEntity::class], version = 1)
abstract class CountriesDatabase: RoomDatabase() {

    abstract fun countriesDao(): CountriesDao
    companion object {
        private var INSTANCE: CountriesDatabase? = null

        fun getInstance(context: Context): CountriesDatabase? {
            if (INSTANCE == null) {
                synchronized(CountriesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CountriesDatabase::class.java, "countriesdata.db")
                        .build()
                }
            }
            return INSTANCE as CountriesDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}