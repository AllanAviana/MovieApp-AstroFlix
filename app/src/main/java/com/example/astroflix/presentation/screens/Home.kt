package com.example.astroflix.presentation.screens

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
import com.example.astroflix.presentation.viewModel.HomeViewModel
import com.example.astroflix.presentation.navegation.AstroflixRoutes
import com.example.astroflix.ui.theme.AstroFlixTypography
import com.example.astroflix.ui.theme.darkGray
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    moviesByGenre: Map<String, List<Movie>>,
    mainCardImageUrl: Movie
               ) {



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(darkGray, Color.Black)
                )
            )
            .padding(8.dp, top = 48.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item { SearchBar() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { MainCard(movie = mainCardImageUrl, navController = navController) }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            moviesByGenre.forEach { (genre, movies) ->
                item { GenreSection(genre, movies, navController) }
            }
        }
    }
}

@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = "",
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(52.dp)
            ,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { },
            leadingIcon = {
                val logo: Painter = painterResource(id = R.drawable.logo)
                Image(
                    painter = logo,
                    contentDescription = "Logo",
                    modifier = Modifier.size(48.dp),
                    alignment = Alignment.CenterStart
                )
            },
            placeholder = { Text(text = stringResource(R.string.search), color = Color.LightGray, fontSize = 16.sp) }
        )
    }
}


@Composable
fun MainCard(movie: Movie, navController: NavController ) {
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

            .offset(x = -8.dp, y = -3.dp)
            .padding(bottom = 2.dp),
        contentAlignment = Alignment.Center
    ) {

            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = "main movie",
                modifier = Modifier
                    .fillMaxSize()
                    .size(width = 405.dp, height = 471.dp)
                    .clickable(onClick = {navController.navigate("${AstroflixRoutes.SecondScreen.route}/$encodedMovieJson")}),
                contentScale = ContentScale.Crop
            )

    }
}


@Composable
fun GenreSection(genre: String, movies: List<Movie>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = genre,
            style = AstroFlixTypography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(movies) { movie -> Body(movie, navController) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Body(movie: Movie, navController: NavController,) {
    val image = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
    val movieJson = Gson().toJson(movie)
    val encodedMovieJson = URLEncoder.encode(movieJson, StandardCharsets.UTF_8.toString())

    ElevatedCard(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(end = 16.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = Color.Black.copy(alpha = 1f),
                spotColor = Color.Black.copy(alpha = 1f)
            )
            .offset(x = -10.dp, y = -5.dp),
        elevation = CardDefaults.elevatedCardElevation(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = "movie poster",
            modifier = Modifier
                .size(width = 190.dp, height = 280.dp)
                .fillMaxSize()
                .clickable(onClick = {navController.navigate("${AstroflixRoutes.SecondScreen.route}/$encodedMovieJson")})
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

