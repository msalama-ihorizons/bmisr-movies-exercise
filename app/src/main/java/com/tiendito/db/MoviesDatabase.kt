package com.tiendito.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tiendito.api.Movie

/**
 * Created by Mohamed Salama on 9/6/2020.
 */

@Database(entities = [Movie::class], version = 3, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}