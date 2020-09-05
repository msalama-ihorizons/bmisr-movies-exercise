package com.tiendito.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tiendito.api.Movie
import com.tiendito.api.MoviesApis
import com.tiendito.model.Resource
import com.tiendito.utils.Constants.API_KEY
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesApis: MoviesApis) {

    fun loadPlayingNowMovies(): LiveData<Resource<List<Movie>>> {

        return liveData {
            emit(Resource.loading(null))

            val result = moviesApis.getNowPlayingMovies(API_KEY)

            emit(Resource.complete(null))

            if (result.isSuccessful)
                emit(Resource.success(result.body()?.movies?.sortedBy { it.title }))
            else
                emit(Resource.error(result.message(), null))

        }
    }
}