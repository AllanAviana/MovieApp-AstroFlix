package com.example.astroflix.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.astroflix.model.Movie
import com.example.astroflix.R
import com.example.astroflix.presentation.ViewModel.HomeViewModel
import com.example.astroflix.presentation.navigation.AstroflixRoutes
import com.example.astroflix.presentation.navigation.DesignNavagationBar
import com.example.astroflix.ui.theme.AstroFlixTypography
import com.example.astroflix.ui.theme.darkGray
import com.example.astroflix.util.Constants.PaddingSmall
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    moviesByGenre: Map<String, List<Movie>>,
    mainCardImageUrl: Movie,
    searchQuery: String,
    filteredMovies: List<Movie>
) {
    Scaffold(
        bottomBar = {
            DesignNavagationBar(navController)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(darkGray, Color.Black)
                    )
                )
                .padding(PaddingSmall, top = 48.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    SearchBar(
                        searchQuery = searchQuery,
                        onSearchQueryChange = viewModel::updateSearchQuery
                    )
                }
                if (filteredMovies.isNotEmpty()) {
                    items(filteredMovies) { movie ->
                        SearchOption(
                            movie = movie,
                            navController = navController
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }

                item { MainCard(
                    movie = mainCardImageUrl,
                    navController = navController,
                    viewModel
                ) }

                item { Spacer(
                    modifier = Modifier.height(16.dp)
                ) }

                moviesByGenre.forEach { (genre, movies) ->
                    item { GenreSection(
                        genre,
                        movies,
                        navController,
                        viewModel) }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = searchQuery,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(52.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = onSearchQueryChange,
            leadingIcon = {
                val logo: Painter = painterResource(id = R.drawable.logo)
                Image(
                    painter = logo,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    alignment = Alignment.CenterStart
                )
            },
            placeholder = { Text(text = stringResource(R.string.search), color = Color.LightGray, fontSize = 16.sp) }
        )
    }
}

@Composable
fun SearchOption(
    movie: Movie,
    navController: NavController
) {
    Text(
        text = movie.title ?: "No Title",
        fontSize = 12.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingSmall)
            .background(Color.Gray.copy(alpha = 0.2f))
            .clickable {
                val movieJson = Gson().toJson(movie)
                val encodedMovieJson = URLEncoder.encode(movieJson, StandardCharsets.UTF_8.toString())
                navController.navigate("${AstroflixRoutes.SecondScreen.route}/$encodedMovieJson")
            }
    )
}

@Composable
fun MainCard(movie: Movie, navController: NavController, viewModel: HomeViewModel) {
    val image = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
    val movieJson = Gson().toJson(movie)
    val encodedMovieJson = URLEncoder.encode(movieJson, StandardCharsets.UTF_8.toString())
    Box(
        modifier = Modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(32.dp),
                ambientColor = Color.Black.copy(alpha = 1f),
                spotColor = Color.Black.copy(alpha = 1f)
            )

            .offset(x = -PaddingSmall, y = -3.dp)
            .padding(bottom = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .size(width = 405.dp, height = 471.dp)
                    .clickable(onClick = {
                        navController.navigate("${AstroflixRoutes.SecondScreen.route}/$encodedMovieJson")
                        viewModel.platforms(movie)}
                    ),
                contentScale = ContentScale.Crop
            )
    }
}


@Composable
fun GenreSection(genre: String, movies: List<Movie>, navController: NavController, viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = PaddingSmall)
    ) {
        Text(
            text = genre,
            style = AstroFlixTypography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(movies) { movie -> Body(movie, navController, viewModel ) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Body(movie: Movie, navController: NavController, viewModel: HomeViewModel) {
    val image = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
    val movieJson = Gson().toJson(movie)
    val encodedMovieJson = URLEncoder.encode(movieJson, StandardCharsets.UTF_8.toString())

    ElevatedCard(
        onClick = {


        },
        modifier = Modifier
            .padding(end = 16.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(PaddingSmall),
                ambientColor = Color.Black.copy(alpha = 1f),
                spotColor = Color.Black.copy(alpha = 1f)
            )
            .offset(x = -10.dp, y = -5.dp),
        elevation = CardDefaults.elevatedCardElevation(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = null,
            modifier = Modifier
                .size(width = 190.dp, height = 280.dp)
                .fillMaxSize()
                .clickable(onClick = {
                    navController.navigate("${AstroflixRoutes.SecondScreen.route}/$encodedMovieJson")
                    viewModel.platforms(movie)})
                .clip(RoundedCornerShape(PaddingSmall)),
            contentScale = ContentScale.Crop
        )
    }
}

