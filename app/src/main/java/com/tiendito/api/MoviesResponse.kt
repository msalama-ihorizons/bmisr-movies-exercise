package com.tiendito.api

import com.google.gson.annotations.SerializedName

data class MoviesResponse(@SerializedName("results") val movies: List<Movie>)

data class Movie(
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String
)