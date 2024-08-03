package com.example.astroflix.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.astroflix.R
import com.example.astroflix.model.Movie
import com.example.astroflix.presentation.ViewModel.FavoriteViewModel
import com.example.astroflix.presentation.navigation.AstroflixRoutes
import com.example.astroflix.ui.theme.AstroFlixTypography
import com.example.astroflix.ui.theme.darkGray
import com.example.astroflix.ui.theme.heartColor
import com.example.astroflix.util.Constants.PaddingSmall
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FavoriteScreen(favoriteMovies: List<Movie>, favoriteViewModel: FavoriteViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(darkGray, Color.Black),

                    )
            )
    ) {
        val image: Painter = painterResource(id = R.drawable.higher)

        val back: Painter = painterResource(id = R.drawable.back)

        Box() {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = back,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = 32.dp, y = 32.dp)
                    .clickable {navController.navigateUp() }
            )
        }

        Box(){
            BodyFavoriteScreen(favoriteMovies, favoriteViewModel, navController)

        }
    }
}

@Composable
fun BodyFavoriteScreen(
    favoriteMovies: List<Movie>,
    favoriteViewModel: FavoriteViewModel,
    navController: NavController
) {

    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.favorite_title1),
            style = AstroFlixTypography.titleMedium
        )
        Text(
            text = stringResource(R.string.favorite_title2),
            style = AstroFlixTypography.titleMedium
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            items(favoriteMovies.chunked(2)) { item ->
                RowContent(item, favoriteViewModel, navController)
                Spacer(modifier = Modifier.height(PaddingSmall))
            }
        }
    }
}


@Composable
fun RowContent(
    movie: List<Movie>,
    favoriteViewModel: FavoriteViewModel,
    navController: NavController
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (movie in movie) {
            DesignImageWithButton(movie, favoriteViewModel, navController)
        }

    }
}

@Composable
fun DesignImageWithButton(
    movie: Movie,
    favoriteViewModel: FavoriteViewModel,
    navController: NavController, ) {
    Box (modifier = Modifier.padding(top = PaddingSmall)){
        DesignImage(movie, navController)
        FavoriteScreenButton(modifier = Modifier
            .align(Alignment.TopEnd)
            .offset(y = -PaddingSmall, x = 4.dp),
            favoriteViewModel,
            movie

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignImage(movie: Movie, navController: NavController) {
    val image= "https://image.tmdb.org/t/p/w500${movie.poster_path}"
    val movieJson = Gson().toJson(movie)
    val encodedMovieJson = URLEncoder.encode(movieJson, StandardCharsets.UTF_8.toString())

    ElevatedCard(
        onClick = { /* TODO */ },
        elevation = CardDefaults.elevatedCardElevation(16.dp),
        modifier = Modifier
            .padding(PaddingSmall)
            .size(width = 180.dp, height = 260.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                BorderStroke(2.dp, Color.Gray),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier

                .clickable(onClick = { navController.navigate("${AstroflixRoutes.SecondScreen.route}/$encodedMovieJson") })
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

@Composable
fun FavoriteScreenButton(modifier: Modifier = Modifier, favoriteViewModel: FavoriteViewModel, movie: Movie) {

    IconButton(
        onClick = { favoriteViewModel.removeFavoriteMovie(movie) },
        modifier = modifier

            .size(46.dp),
        colors = IconButtonDefaults.iconButtonColors(heartColor)
    ) {
        Image(
            painter = painterResource(id = R.drawable.heartwhite),
            contentDescription = null,
            modifier = Modifier.size(27.dp)
        )
    }
}

