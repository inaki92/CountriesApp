package com.inaki.countries.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountriesEntity (
    @PrimaryKey var name: String,
    @ColumnInfo(name = "capital")var capital: String,
    @ColumnInfo(name = "callingCode")var callingCodes: String,
    @ColumnInfo(name = "region")var region: String,
    @ColumnInfo(name = "subRegion")var subRegion: String,
    @ColumnInfo(name = "timeZone")var timeZones: String,
    @ColumnInfo(name = "currencies")var currencies: String,
    @ColumnInfo(name = "languages")var languages: String,
    @ColumnInfo(name = "flag")var flagFile: String
    )