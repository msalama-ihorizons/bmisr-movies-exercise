package com.tiendito.ui.moviedetails.similar

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.tiendito.model.Status
import com.tiendito.ui.moviedetails.MovieDetailsViewModel
import com.tiendito.ui.movieslist.MoviesListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.similar_movies_fragment.progressBar

/**
 * Created by Mohamed Salama on 9/6/2020.
 */

class SimilarMoviesFragment: MoviesListFragment()  {

    private val moviesDetailsViewModel: MovieDetailsViewModel by activityViewModels()

    override fun handleObservers() {
        moviesDetailsViewModel.similarMoviesLiveData?.observe(viewLifecycleOwner, Observer { resource->
            when(resource.status) {
                Status.SUCCESS -> moviesAdapter.items = resource.data
                Status.ERROR -> Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }

}