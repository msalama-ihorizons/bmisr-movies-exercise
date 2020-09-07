package com.tiendito.ui.movieslist

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
import com.tiendito.ui.adpater.MoviesAdapter
import com.tiendito.ui.moviedetails.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_list_fragment.*

/**
 * Created by Mohamed Salama on 9/7/2020.
 */
open class MoviesListFragment: Fragment()  {

    private val moviesViewModel: MoviesViewModel by activityViewModels()
    protected lateinit var moviesAdapter: MoviesAdapter

    companion object {
        private const val NUMBER_OF_COL = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_list_fragment, container, false)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesAdapter = MoviesAdapter(
            context,
            OnRecyclerItemClickListener { pos ->

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

        moviesRV.adapter =  moviesAdapter
        moviesRV.layoutManager = GridLayoutManager(context, NUMBER_OF_COL)
        moviesRV.setHasFixedSize(true)

        handleObservers()
    }

    open fun handleObservers() {
        moviesViewModel.moviesListLiveData.observe(viewLifecycleOwner, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS -> moviesAdapter.items = resources.data
                Status.ERROR -> Toast.makeText(context, resources.message, Toast.LENGTH_LONG).show()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }
}