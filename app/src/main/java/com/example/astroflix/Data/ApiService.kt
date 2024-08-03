package com.example.astroflix.Data

import com.example.astroflix.model.MovieResponse
import com.example.astroflix.model.WatchProvidersResponse
import com.example.astroflix.util.Constants.API_KEY
import com.example.astroflix.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface MovieApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Header("Authorization") authorization: String = API_KEY
    ): MovieResponse

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getWatchProviders(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") authorization: String = API_KEY
    ): WatchProvidersResponse
}
val movieService: MovieApiService = retrofit.create(MovieApiService::class.java)