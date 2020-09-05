package com.tiendito.ui.moviedetails.similar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.tiendito.bmisrmovies.R
import com.tiendito.model.Status
import com.tiendito.ui.moviedetails.MovieDetailsActivity
import com.tiendito.ui.moviedetails.MovieDetailsViewModel
import com.tiendito.ui.movieslist.MainActivity
import com.tiendito.ui.movieslist.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.similar_movies_fragment.*
import kotlinx.android.synthetic.main.similar_movies_fragment.progressBar

/**
 * Created by Mohamed Salama on 9/6/2020.
 */

@AndroidEntryPoint
class SimilarMoviesFragment: Fragment()  {

    private val moviesDetailsViewModel: MovieDetailsViewModel by activityViewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    companion object {
        private const val NUMBER_OF_COL = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.similar_movies_fragment, container, false)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesAdapter = MoviesAdapter(context, OnRecyclerItemClickListener { pos ->

            startActivity(
                MovieDetailsActivity.newIntent(
                    requireActivity(),
                    moviesAdapter.items[pos].id
                )
            )

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        similarMoviesRV.adapter =  moviesAdapter
        similarMoviesRV.layoutManager = GridLayoutManager(context, NUMBER_OF_COL)
        similarMoviesRV.setHasFixedSize(true)

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