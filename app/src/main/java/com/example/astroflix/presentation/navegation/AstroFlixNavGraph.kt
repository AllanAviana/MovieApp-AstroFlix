package com.example.astroflix.presentation.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.astroflix.model.Movie
import com.example.astroflix.presentation.viewModel.FavoriteViewModel
import com.example.astroflix.presentation.viewModel.HomeViewModel
import com.example.astroflix.presentation.screens.HomeScreen
import com.example.astroflix.presentation.screens.detailsScreen
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AstroFlixNavGraph(
    navController: NavHostController,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    viewModelFavorite: FavoriteViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val moviesByGenre by viewModel.moviesByGenre.collectAsState()
    val mainCardMovie by viewModel.mainCardMovie.collectAsState()
    val favoriteMovies by  viewModelFavorite.favoriteMovies.collectAsState()

    NavHost(
        navController = navController,
        startDestination = AstroflixRoutes.FirstScreen.route
    ) {
        composable(AstroflixRoutes.FirstScreen.route) {

            HomeScreen(
                navController = navController,
                moviesByGenre = moviesByGenre,
                mainCardImageUrl = mainCardMovie,
                viewModel = viewModel
            )
        }
        composable("${AstroflixRoutes.SecondScreen.route}/{movieJson}") { backStackEntry ->
            val movieJson = backStackEntry.arguments?.getString("movieJson")
            movieJson?.let {
                val decodedMovieJson = URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                val movie = Gson().fromJson(decodedMovieJson, Movie::class.java)
                detailsScreen(movie = movie, navController = navController, viewModelFavorite = viewModelFavorite)
            }
        }
    }
}

enum class AstroflixRoutes(val route: String) {
    FirstScreen("first_screen"),
    SecondScreen("second_screen"),
    ThirdScreen("third_screen")
}
