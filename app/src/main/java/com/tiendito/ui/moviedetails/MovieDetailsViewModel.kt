package com.tiendito.ui.moviedetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.tiendito.repository.MoviesRepository
import com.tiendito.ui.moviedetails.MovieDetailsActivity.Companion.EXTRA_MOVIE_ID

class MovieDetailsViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle?
) : ViewModel() {

    private val movieID = savedStateHandle?.getLiveData<Int>(EXTRA_MOVIE_ID)

    val movieDetailsLiveData = movieID?.switchMap {
            movieID->   moviesRepository.loadMovieDetails(movieID)
    }

    val movieCastLiveData = movieID?.switchMap {
            movieID->   moviesRepository.loadMovieCast(movieID)
    }
}