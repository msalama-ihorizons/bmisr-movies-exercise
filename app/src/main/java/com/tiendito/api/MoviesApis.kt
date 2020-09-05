package com.tiendito.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApis {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String): Response<MoviesResponse>
}