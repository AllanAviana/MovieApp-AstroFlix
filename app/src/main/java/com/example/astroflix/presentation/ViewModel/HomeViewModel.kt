package com.example.astroflix.presentation.ViewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astroflix.model.Movie
import com.example.astroflix.Data.movieService
import com.example.astroflix.Data.Genres
import com.example.astroflix.model.CountryWatchProviders
import com.example.astroflix.util.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie: StateFlow<List<Movie>> get() = _movie.asStateFlow()

    private val _moviesByGenre = MutableStateFlow<Map<String, List<Movie>>>(emptyMap())
    val moviesByGenre: StateFlow<Map<String, List<Movie>>> get() = _moviesByGenre.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery.asStateFlow()

    private val _filteredMovies = MutableStateFlow<List<Movie>>(emptyList())
    val filteredMovies: StateFlow<List<Movie>> get() = _filteredMovies.asStateFlow()

    private val _mainCardMovie = MutableStateFlow<Movie>(
        Movie(
            id = 0,
            title = "",
            overview = "",
            poster_path = "",
            backdrop_path = "",
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

    private val _plataforms = MutableStateFlow<CountryWatchProviders?>(null)

    private val _highlightList = MutableStateFlow<List<Movie>>(emptyList())
    val highlightList: StateFlow<List<Movie>> get() = _highlightList.asStateFlow()


    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _movie.value = fetchMoviesFromApi()
            updateMainCardMovie(_movie.value )
            groupMoviesByGenre(_movie.value )
            addHighlightList(_movie.value)

        }
    }

    private suspend fun fetchMoviesFromApi(): List<Movie> = withContext(Dispatchers.IO) {
        val totalPages = 60
        val allMovies = mutableSetOf<Movie>()

        val deferreds = (1..totalPages).map { page ->
            async {
                val response = movieService.getMovies(
                    page = page,
                    authorization = API_KEY
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

    fun platforms(movie: Movie) {
        viewModelScope.launch {
            try {
                val response = movieService.getWatchProviders(movie.id)
                val providers = response.results["BR"]
                _plataforms.value = providers
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching watch providers", e)
            }
        }
    }

    fun getPlatforms(): String? {
        val platforms = _plataforms.value
        val logoPath = platforms?.flatrate?.firstOrNull()?.logo_path
        return logoPath
    }

    private fun addHighlightList(movie: List<Movie>) {
        val highlightList = mutableListOf<Movie>()
        movie.forEach { movie ->
            if (movie.vote_average >= 7.5 && movie.vote_count >= 100) {
                highlightList.add(movie)
            }
        }
        _highlightList.value = highlightList
    }
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterMovies(query)
    }

    private fun filterMovies(query: String) {
        val filteredList = if (query.isBlank()) {
            emptyList()
        } else {
            _movie.value.filter { movie ->
                movie.title?.contains(query, ignoreCase = true) == true
            }
        }
        _filteredMovies.value = filteredList
    }

}
