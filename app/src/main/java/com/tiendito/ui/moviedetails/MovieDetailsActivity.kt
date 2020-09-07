package com.tiendito.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.iarcuschin.simpleratingbar.SimpleRatingBar
import com.irozon.sneaker.Sneaker
import com.like.LikeButton
import com.like.OnLikeListener
import com.tiendito.api.Movie
import com.tiendito.bmisrmovies.R
import com.tiendito.model.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_details_activity.*
import java.util.*


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private val moviesDetailsViewModel: MovieDetailsViewModel by viewModels()
    private var movie: Movie? = null

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

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        handleActions()

        handleObservers()
    }

    private fun handleActions() {

        ratingBar.setOnRatingBarChangeListener(object : SimpleRatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(
                simpleRatingBar: SimpleRatingBar?,
                rating: Float,
                fromUser: Boolean
            ) {
                moviesDetailsViewModel.rateMovie(rating)
            }

        })


        btnAddToFav.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                moviesDetailsViewModel.addToFavourites(true, movie)
            }

            override fun unLiked(likeButton: LikeButton?) {
                moviesDetailsViewModel.addToFavourites(false, movie)
            }

        })

    }

    private fun handleObservers() {
        moviesDetailsViewModel.movieDetailsLiveData?.observe(this, Observer { resources ->

            when (resources.status) {
                Status.SUCCESS -> {
                    movie = resources.data

                    movie?.let {
                        title = it.title

                        val posterUrl = getString(R.string.movie_poster_url, it.backdropPath)
                        Glide.with(this).load(posterUrl)
                            .into(moviePoster)

                        movieTitle.text = it.title
                        movieOverView.text = it.overview
                        movieVoteAverage.text = it.voteAverage.toString()
                        movieReleaseDate.text = it.releaseDate
                        movieGenres.text = TextUtils.join(", ", it.genres.map { genre-> genre.name })
                    }


                }
                Status.ERROR -> Toast.makeText(this, resources.message, Toast.LENGTH_LONG).show()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })

        moviesDetailsViewModel.ratingMovieLiveData.observe(this, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS ->  Sneaker.with(this)
                    .setTitle("Success")
                    .setMessage("Rating is submitted!")
                    .sneakSuccess()
                Status.ERROR ->  Sneaker.with(this)
                    .setTitle("Error")
                    .setMessage("There is an error, please try again later")
                    .sneakError()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }

        })

        moviesDetailsViewModel.favouriteMovieLiveDate?.observe(this, Observer {
            btnAddToFav.isLiked = it
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}