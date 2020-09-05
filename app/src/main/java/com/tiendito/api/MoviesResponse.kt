package com.tiendito.api

import com.google.gson.annotations.SerializedName

data class MoviesResponse(@SerializedName("results") val movies: List<Movie>)

data class Genre(val id: Int, val name: String)


data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("genres") val genres: List<Genre>
)