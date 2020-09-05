package com.tiendito.ui.movieslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.tiendito.bmisrmovies.R
import com.tiendito.model.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    companion object {
        private const val NUMBER_OF_COL = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesAdapter = MoviesAdapter(this, OnRecyclerItemClickListener {

        })

        moviesRV.layoutManager = GridLayoutManager(this, NUMBER_OF_COL)
        moviesRV.adapter = moviesAdapter

        moviesViewModel.moviesList.observe(this, Observer {  resources ->
            when (resources.status) {
                Status.SUCCESS -> moviesAdapter.items = resources.data
                Status.ERROR -> Toast.makeText(this, resources.message, Toast.LENGTH_LONG).show()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }
}