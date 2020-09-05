package com.tiendito.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tiendito.api.*
import com.tiendito.model.Resource
import com.tiendito.utils.Constants.API_KEY
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApis: MoviesApis,
    private val sessionRepository: SessionRepository
) {

    fun loadPlayingNowMovies(): LiveData<Resource<List<Movie>>> {

        return liveData {
            emit(Resource.loading(null))

            val result = moviesApis.getNowPlayingMovies(apiKey = API_KEY)

            emit(Resource.complete(null))

            if (result.isSuccessful)
                emit(Resource.success(result.body()?.movies?.sortedBy { it.title }))
            else
                emit(Resource.error(result.message(), null))

        }
    }

    fun loadMovieDetails(movieId: Int): LiveData<Resource<Movie>> {

        return liveData {

            emit(Resource.loading(null))

            val result = moviesApis.getMovieDetails(movieId = movieId, apiKey = API_KEY)

            emit(Resource.complete(null))

            if (result.isSuccessful)
                emit(Resource.success(result.body()))
            else
                emit(Resource.error(result.message(), null))

        }
    }

    fun loadMovieCast(movieId: Int): LiveData<Resource<List<Cast>>> {

        return liveData {

            emit(Resource.loading(null))

            val result = moviesApis.getMovieCast(movieId = movieId, apiKey = API_KEY)

            emit(Resource.complete(null))

            if (result.isSuccessful)
                emit(Resource.success(result.body()?.cast))
            else
                emit(Resource.error(result.message(), null))

        }
    }

    fun rateMovie(movieId: Int, ratingValue: Float): LiveData<Resource<RateResponse>> {
        return liveData {
            emit(Resource.loading(null))


            if (sessionRepository.isExpired()) {
                val sessionResult = moviesApis.generateGuestSession(apiKey = API_KEY)
                if (sessionResult.isSuccessful)
                    sessionRepository.saveGuestSession(sessionResult.body()?.guestSessionId)
            }

            val result = moviesApis.rateMovie(
                movieId = movieId,
                apiKey = API_KEY,
                guestSessionId = sessionRepository.getGuestSession() ?: "",
                rateRequest = RateRequest(ratingValue)
            )

            emit(Resource.complete(null))

            if (result.isSuccessful)
                emit(Resource.success(result.body()))
            else
                emit(Resource.error(result.message(), null))
        }
    }
}