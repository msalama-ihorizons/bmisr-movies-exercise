package com.tiendito.api

import retrofit2.Response
import retrofit2.http.*

interface MoviesApis {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<Movie>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<CreditsResponse>

    @GET("authentication/guest_session/new")
    suspend fun generateGuestSession(@Query("api_key") apiKey: String): Response<GuestSessionResponse>

    @POST("movie/{movie_id}/rating")
    suspend fun rateMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("guest_session_id") guestSessionId: String,
        @Body rateRequest: RateRequest

    ): Response<RateResponse>
}