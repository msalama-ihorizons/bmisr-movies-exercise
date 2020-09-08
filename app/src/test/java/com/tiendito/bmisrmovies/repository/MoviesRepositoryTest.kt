package com.tiendito.bmisrmovies.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tiendito.bmisrmovies.api.Movie
import com.tiendito.bmisrmovies.api.MoviesApis
import com.tiendito.bmisrmovies.api.MoviesResponse
import com.tiendito.bmisrmovies.db.MoviesDao
import com.tiendito.bmisrmovies.db.MoviesDatabase
import com.tiendito.bmisrmovies.model.Resource
import com.tiendito.bmisrmovies.mock
import com.tiendito.bmisrmovies.utils.Constants.API_KEY
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import retrofit2.Response

/**
 * Created by Mohamed Salama on 9/8/2020.
 */

@RunWith(AndroidJUnit4::class)
class MoviesRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesRepository: MoviesRepository

    private val sessionRepository = Mockito.mock(SessionRepository::class.java)
    private val service = Mockito.mock(MoviesApis::class.java)
    private val dao = Mockito.mock(MoviesDao::class.java)

    @Before
    fun init() {
        val db = Mockito.mock(MoviesDatabase::class.java)
        Mockito.`when`(db.moviesDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        moviesRepository = MoviesRepository(service, sessionRepository, dao)
    }


    @Test
    fun loadPlayingNowMoviesTest() {
        val observer = mock<Observer<Resource<List<Movie>>>>()

        val moviesResponse = MoviesResponse(emptyList())

        val apiResponse = Response.success(moviesResponse)

        runBlocking {
            Mockito.`when`(service.getNowPlayingMovies(apiKey = API_KEY)).thenReturn(apiResponse)

            moviesRepository.loadPlayingNowMovies().observeForever(observer)

            Mockito.verify(observer).onChanged(Resource.loading(null))
            Mockito.verify(observer).onChanged(Resource.complete(null))
            Mockito.verify(observer).onChanged(Resource.success(emptyList()))
        }
    }

    @Test
    fun isFavourite_Return_True_Test() {
        val movie = MutableLiveData<Movie>()
        val observer = mock<Observer<Boolean>>()

        movie.postValue(createMovie(550))

        Mockito.`when`(dao.loadMovieById(550)).thenReturn(movie)

        moviesRepository.isFavouriteMovie(550).observeForever(observer)

        Mockito.verify(observer).onChanged(true)

    }

    @Test
    fun isFavourite_Return_False_Test() {
        val movie = MutableLiveData<Movie>()
        val observer = mock<Observer<Boolean>>()

        movie.postValue(null)

        Mockito.`when`(dao.loadMovieById(550)).thenReturn(movie)

        moviesRepository.isFavouriteMovie(550).observeForever(observer)

        Mockito.verify(observer).onChanged(false)

    }

    @Test
    fun loadAllFavouritesTest() {
        val observer = mock<Observer<Resource<List<Movie>>>>()

        val movies = MutableLiveData<List<Movie>>()

        Mockito.`when`(dao.loadMovies()).thenReturn(movies)

        moviesRepository.loadFavouriteMovies().observeForever(observer)

        Mockito.verify(observer).onChanged(Resource.loading(null))

        movies.postValue(emptyList())

        Mockito.verify(observer).onChanged(Resource.complete(null))
        Mockito.verify(observer).onChanged(Resource.success(emptyList()))

    }

    private fun createMovie(id: Int) : Movie {
        return Movie(
            id = id,
            title = "whiplash",
            releaseDate = "2014-10-20",
            voteAverage = 5.5,
            posterPath = "",
            backdropPath = "",
            overview = "",
            genres = emptyList()
        )
    }

}