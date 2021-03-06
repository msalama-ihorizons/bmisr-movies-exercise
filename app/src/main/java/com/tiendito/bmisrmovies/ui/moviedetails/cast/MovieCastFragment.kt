package com.tiendito.bmisrmovies.ui.moviedetails.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.tiendito.bmisrmovies.R
import com.tiendito.bmisrmovies.model.Status
import com.tiendito.bmisrmovies.ui.adpater.MovieCastAdapter
import com.tiendito.bmisrmovies.ui.moviedetails.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_cast_fragment.*

/**
 * Created by Mohamed Salama on 9/5/2020.
 */

@AndroidEntryPoint
class MovieCastFragment: Fragment() {

    private lateinit var movieCastAdapter: MovieCastAdapter
    private val moviesDetailsViewModel: MovieDetailsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieCastAdapter = MovieCastAdapter(
            context,
            OnRecyclerItemClickListener {

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_cast_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieCastRV.adapter = movieCastAdapter

        moviesDetailsViewModel.movieCastLiveData?.observe(viewLifecycleOwner, Observer {resource ->

            when(resource.status) {
                Status.SUCCESS -> movieCastAdapter.items = resource.data
                Status.LOADING ->  progressBar.visibility = View.VISIBLE
                Status.COMPLETE ->  progressBar.visibility = View.GONE
            }
        })
    }
}