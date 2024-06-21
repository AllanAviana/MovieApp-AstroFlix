package com.example.astroflix.Data

import com.example.astroflix.model.MovieResponse
import com.example.astroflix.model.WatchProvidersResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
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
        @Header("Authorization") authorization: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZjJmYzk2MDcxNTgzYTljNTBjODljMjA3MTMyZmJkMiIsInN1YiI6IjY2NmI4MjI5ZjYyN2NiYzQ4YTc2MWMzYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.__ax1wJSoWs2aN3GeQrOcGcWt9gOxMnh_DKxubRbkPE"
    ): MovieResponse

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getWatchProviders(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") authorization: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZjJmYzk2MDcxNTgzYTljNTBjODljMjA3MTMyZmJkMiIsInN1YiI6IjY2NmI4MjI5ZjYyN2NiYzQ4YTc2MWMzYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.__ax1wJSoWs2aN3GeQrOcGcWt9gOxMnh_DKxubRbkPE"
    ): WatchProvidersResponse
}
val movieService: MovieApiService = retrofit.create(MovieApiService::class.java)

