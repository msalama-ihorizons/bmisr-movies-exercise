package com.tiendito.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.tiendito.bmisrmovies.R
import com.tiendito.model.Status
import com.tiendito.ui.movieslist.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_details_activity.*
import kotlinx.android.synthetic.main.movie_item.view.*

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private val moviesDetailsViewModel: MovieDetailsViewModel by viewModels()

    companion object {
        const val EXTRA_MOVIE_ID = "extraMovieID"

        fun newIntent(context: Context?, movieId: Int?): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.movie_details_activity)

        moviesDetailsViewModel.movieDetailsLiveData?.observe(this, Observer { resources ->

            when (resources.status) {
                Status.SUCCESS -> {
                    val movie = resources.data

                    movie?.let {
                        val posterUrl = getString(R.string.movie_poster_url, it.backdropPath)
                        Glide.with(this).load(posterUrl)
                            .into(moviePoster)

                        movieTitle.text = it.title
                        movieOverView.text = it.overview
                        movieVoteAverage.text = it.voteAverage.toString()
                        movieReleaseDate.text = it.releaseDate
                    }


                }
                Status.ERROR -> Toast.makeText(this, resources.message, Toast.LENGTH_LONG).show()
                Status.LOADING ->  progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }
}