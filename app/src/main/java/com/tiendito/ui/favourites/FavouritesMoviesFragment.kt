package com.tiendito.ui.favourites

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.tiendito.model.Status
import com.tiendito.ui.movieslist.MoviesListFragment
import com.tiendito.ui.movieslist.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_list_fragment.*

/**
 * Created by Mohamed Salama on 9/7/2020.
 */

class FavouritesMoviesFragment: MoviesListFragment() {

    private val moviesViewModel: MoviesViewModel by activityViewModels()

    override fun handleObservers() {
        moviesViewModel.favMoviesListLiveData.observe(viewLifecycleOwner, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS -> moviesAdapter.items = resources.data
                Status.ERROR -> Toast.makeText(context, resources.message, Toast.LENGTH_LONG).show()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }
}