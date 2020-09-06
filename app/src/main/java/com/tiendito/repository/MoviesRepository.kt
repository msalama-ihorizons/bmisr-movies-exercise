package com.tiendito.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.tiendito.api.*
import com.tiendito.db.MoviesDao
import com.tiendito.model.Resource
import com.tiendito.utils.Constants.API_KEY
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApis: MoviesApis,
    private val sessionRepository: SessionRepository,
    private val moviesDao: MoviesDao

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

    fun loadSimilarMovies(movieId: Int): LiveData<Resource<List<Movie>>> {

        return liveData {
            emit(Resource.loading(null))

            val result = moviesApis.getSimilarMovies(movieId = movieId, apiKey = API_KEY)

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

    suspend fun addFavouriteMovie(isFavourite: Boolean, movie: Movie?) {
        if (isFavourite)
            moviesDao.insert(movie)
        else
            moviesDao.deleteMovie(movie?.id)
    }

    fun isFavouriteMovie(movieId: Int): LiveData<Boolean> {

        val resultLiveData = MediatorLiveData<Boolean>()

        resultLiveData.addSource(moviesDao.loadMovieById(movieId)) { movie ->
            movie?.let {
                resultLiveData.value = true
                return@addSource
            }
            resultLiveData.value = false

        }

        return resultLiveData
    }

    fun loadFavouriteMovies(): LiveData<Resource<List<Movie>>> {
        val resultLiveData = MediatorLiveData<Resource<List<Movie>>>()
        resultLiveData.value  = Resource.loading(null)

        resultLiveData.addSource(moviesDao.loadMovies()) {
            movies->
            resultLiveData.value = Resource.complete(null)

            resultLiveData.value = Resource.success(movies)
        }
        return resultLiveData
    }
}