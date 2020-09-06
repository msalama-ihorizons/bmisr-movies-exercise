package com.tiendito.ui.favourites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.tiendito.model.Status
import com.tiendito.ui.movieslist.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Mohamed Salama on 9/6/2020.
 */
class FavouritesMoviesActivity: MainActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Favourites"

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, FavouritesMoviesActivity::class.java)
        }
    }

    override fun handleObservers() {

        moviesViewModel.favMoviesListLiveData.observe(this, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS -> moviesAdapter.items = resources.data
                Status.ERROR -> Toast.makeText(this, resources.message, Toast.LENGTH_LONG).show()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}