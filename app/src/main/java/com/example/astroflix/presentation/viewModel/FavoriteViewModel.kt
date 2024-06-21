package com.example.astroflix.presentation.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.astroflix.model.Movie
import com.example.astroflix.R
import com.example.astroflix.ui.theme.likeColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoriteViewModel: ViewModel() {
    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> get() = _favoriteMovies.asStateFlow()


    fun addFavoriteMovie(movie: Movie) {
        _favoriteMovies.value = _favoriteMovies.value + movie
    }

    fun removeFavoriteMovie(movie: Movie) {
        _favoriteMovies.value = _favoriteMovies.value - movie
    }

    fun isFavoriteMovie(movie: Movie): Boolean {
        return favoriteMovies.value.contains(movie)

    }

    @Composable
    fun buttonColor(movie: Movie): Pair<Color, Int> {
        val isFavorite by favoriteMovies.collectAsState()

        val color = if (isFavorite.contains(movie)) likeColor else Color.White
        val image = if (isFavorite.contains(movie)) R.drawable.heartwhite else R.drawable.deslike

        return Pair(color, image)
    }


}