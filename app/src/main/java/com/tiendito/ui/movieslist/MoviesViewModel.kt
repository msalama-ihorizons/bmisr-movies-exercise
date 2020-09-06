package com.tiendito.ui.movieslist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tiendito.repository.MoviesRepository

class MoviesViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val moviesListLiveData = moviesRepository.loadPlayingNowMovies()

    val favMoviesListLiveData = moviesRepository.loadFavouriteMovies()

}