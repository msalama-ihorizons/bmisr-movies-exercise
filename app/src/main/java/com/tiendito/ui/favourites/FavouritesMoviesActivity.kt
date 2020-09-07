package com.tiendito.ui.favourites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tiendito.bmisrmovies.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mohamed Salama on 9/6/2020.
 */
@AndroidEntryPoint
class FavouritesMoviesActivity: AppCompatActivity() {

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, FavouritesMoviesActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favouites_activity)
        title = "Favourites"

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
