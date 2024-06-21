package com.example.astroflix.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astroflix.model.Movie
import com.example.astroflix.Data.movieService
import com.example.astroflix.Data.Genres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {


    private val _moviesByGenre = MutableStateFlow<Map<String, List<Movie>>>(emptyMap())
    val moviesByGenre: StateFlow<Map<String, List<Movie>>> get() = _moviesByGenre.asStateFlow()

    private val _mainCardMovie = MutableStateFlow<Movie>(
        Movie(
        id = 0,
        title = "",
        overview = "",
        poster_path = "",
        backdrop_path = null,
        release_date = "",
        vote_average = 0.0f,
        vote_count = 0,
        genre_ids = emptyList(),
        original_language = "",
        original_title = "",
        popularity = 0.0f,
        video = false,
        adult = false
    )
    )
    val mainCardMovie: StateFlow<Movie> get() = _mainCardMovie.asStateFlow()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            val moviesList = fetchMoviesFromApi()
            updateMainCardMovie(moviesList)
            groupMoviesByGenre(moviesList)
        }
    }

    private suspend fun fetchMoviesFromApi(): List<Movie> = withContext(Dispatchers.IO) {
        val totalPages = 60
        val allMovies = mutableSetOf<Movie>()

        val deferreds = (1..totalPages).map { page ->
            async {
                val response = movieService.getMovies(
                    page = page,
                    authorization = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZjJmYzk2MDcxNTgzYTljNTBjODljMjA3MTMyZmJkMiIsInN1YiI6IjY2NmI4MjI5ZjYyN2NiYzQ4YTc2MWMzYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.__ax1wJSoWs2aN3GeQrOcGcWt9gOxMnh_DKxubRbkPE"
                )
                response.results
            }
        }

        deferreds.forEach { deferred ->
            allMovies.addAll(deferred.await())
        }

        return@withContext allMovies.toList()
    }

    private fun updateMainCardMovie(moviesList: List<Movie>) {
        if (moviesList.isNotEmpty()) {
            _mainCardMovie.value = moviesList.random()
        }
    }

    private fun groupMoviesByGenre(movies: List<Movie>) {
        val genreMap = mutableMapOf<String, MutableList<Movie>>()

        for (genre in Genres.genres.values) {
            genreMap[genre] = mutableListOf()
        }

        for (movie in movies) {
            val firstGenreId = movie.genre_ids.firstOrNull()
            if (firstGenreId != null) {
                val genreName = Genres.genres[firstGenreId] ?: "Unknown"
                genreMap[genreName]?.add(movie)
            }
        }

        _moviesByGenre.value = genreMap
    }


}
