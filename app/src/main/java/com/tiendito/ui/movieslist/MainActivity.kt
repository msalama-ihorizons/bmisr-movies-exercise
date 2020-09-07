package com.tiendito.ui.movieslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import com.tiendito.bmisrmovies.R
import com.tiendito.ui.adpater.MoviesAdapter
import com.tiendito.ui.favourites.FavouritesMoviesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fav -> startActivity(FavouritesMoviesActivity.newIntent(this))
        }
        return true
    }

}