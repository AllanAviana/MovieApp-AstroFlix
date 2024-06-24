package com.example.astroflix.model

data class MovieResponse(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String,
    val vote_average: Float,
    val vote_count: Int,
    val genre_ids: List<Int>,
    val original_language: String?,
    val original_title: String?,
    val popularity: Float,
    val video: Boolean,
    val adult: Boolean
)

data class WatchProvidersResponse(
    val results: Map<String, CountryWatchProviders>
)

data class CountryWatchProviders(
    val link: String,
    val rent: List<Provider>?,
    val buy: List<Provider>?,
    val flatrate: List<Provider>?
)

data class Provider(
    val logo_path: String,
    val provider_id: Int,
    val provider_name: String,
    val display_priority: Int
)