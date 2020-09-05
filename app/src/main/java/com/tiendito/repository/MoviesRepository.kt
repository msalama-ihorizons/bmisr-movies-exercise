package com.tiendito.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tiendito.api.Movie
import com.tiendito.api.MoviesApis
import com.tiendito.model.Resource
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesApis: MoviesApis) {

    fun loadPlayingNowMovies(): LiveData<Resource<List<Movie>>> {

        return liveData {
            emit(Resource.loading(null))

            val result = moviesApis.getNowPlayingMovies(
                "b7d7973004e15ed98d3cce467b8ee646"
            )

            if (result.isSuccessful)
                emit(Resource.success(result.body()?.movies))
            else
                emit(Resource.error(result.message(), null))


            emit(Resource.complete(null))
        }
    }
}