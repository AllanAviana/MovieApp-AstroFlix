package com.example.astroflix.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.astroflix.model.Movie
import com.example.astroflix.presentation.ViewModel.FavoriteViewModel
import com.example.astroflix.presentation.ViewModel.HomeViewModel
import com.example.astroflix.presentation.screens.FavoriteScreen
import com.example.astroflix.presentation.screens.Highlights
import com.example.astroflix.presentation.screens.HomeScreen
import com.example.astroflix.presentation.screens.DetailsScreen
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
    val displayHighlightMovie by viewModel.highlightList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredMovies by viewModel.filteredMovies.collectAsState()



    NavHost(
        navController = navController,
        startDestination = AstroflixRoutes.FirstScreen.route
    ) {
        composable(AstroflixRoutes.FirstScreen.route) {

            HomeScreen(
                navController = navController,
                moviesByGenre = moviesByGenre,
                mainCardImageUrl = mainCardMovie,
                viewModel = viewModel,
                searchQuery = searchQuery,
                filteredMovies = filteredMovies

            )
        }
        composable("${AstroflixRoutes.SecondScreen.route}/{movieJson}") { backStackEntry ->
            val movieJson = backStackEntry.arguments?.getString("movieJson")
            movieJson?.let {
                val decodedMovieJson = URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                val movie = Gson().fromJson(decodedMovieJson, Movie::class.java)
                DetailsScreen(
                    movie = movie,
                    navController = navController,
                    viewModelFavorite = viewModelFavorite,
                    viewModel = viewModel)
            }
        }
        composable(AstroflixRoutes.ThirdScreen.route) {
             FavoriteScreen(
                 favoriteMovies = favoriteMovies,
                 favoriteViewModel = viewModelFavorite,
                 navController = navController)
        }
        composable(AstroflixRoutes.FourthScreen.route) {
           Highlights(
               navController = navController,
               displayHighlightMovie = displayHighlightMovie,
               viewModel = viewModel

           )
        }
    }
}

enum class AstroflixRoutes(val route: String) {
    FirstScreen("first_screen"),
    SecondScreen("second_screen"),
    ThirdScreen("third_screen"),
    FourthScreen("fourth_screen")
}
