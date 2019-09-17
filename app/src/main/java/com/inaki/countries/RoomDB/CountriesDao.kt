package com.inaki.countries.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface CountriesDao {

    @Query("SELECT * from countries")
    fun getAll(): Flowable<List<CountriesEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(country: CountriesEntity)

    @Delete
    fun delete(country: CountriesEntity)
}