package com.tiendito.bmisrmovies.ui.favourites

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.irozon.sneaker.Sneaker
import com.tiendito.bmisrmovies.model.Status
import com.tiendito.bmisrmovies.ui.movieslist.MoviesListFragment
import com.tiendito.bmisrmovies.ui.movieslist.MoviesViewModel
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
                Status.ERROR ->  Sneaker.with(this)
                    .setTitle("Error")
                    .setMessage(resources.message?: "UnKnow Error")
                    .sneakError()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }
}