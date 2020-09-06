package com.tiendito.di

import android.content.Context
import androidx.room.Room
import com.tiendito.db.MoviesDao
import com.tiendito.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Mohamed Salama on 9/6/2020.
 */

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MoviesDatabase {
        return  return Room.databaseBuilder(
            appContext,
            MoviesDatabase::class.java,
            "movies.db"
        ).build()
    }

    @Provides
    fun provideMoviesDao(db: MoviesDatabase): MoviesDao {
        return db.moviesDao()
    }

}