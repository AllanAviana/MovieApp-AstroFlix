package com.example.astroflix.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.astroflix.Data.Genres
import com.example.astroflix.model.Movie
import com.example.astroflix.R
import com.example.astroflix.presentation.ViewModel.FavoriteViewModel
import com.example.astroflix.presentation.navigation.AstroflixRoutes
import com.example.astroflix.presentation.ViewModel.HomeViewModel
import com.example.astroflix.ui.theme.AstroFlixTypography
import com.example.astroflix.ui.theme.borderColor
import com.example.astroflix.ui.theme.lightGray
import com.example.astroflix.util.Constants.PaddingMedium
import com.example.astroflix.util.Constants.PaddingSmall

@Composable
fun DetailsScreen(
    navController: NavController,
    movie: Movie,
    viewModelFavorite: FavoriteViewModel,
    viewModel: HomeViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Gray, Color.Black)
                )
            )
            .padding(top = PaddingMedium, start = PaddingSmall, end = 8.dp)
    ) {
        item {
            ImageDetails(navController, movie)
        }
        item {
            Body(movie)
        }
        item {
            Box {
                StreamingAvailability(viewModel)
                FavoriteButton(viewModelFavorite, movie)
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
@Composable
fun ImageDetails(navController: NavController, movie: Movie){
    Box(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .padding(top = 1.dp)
    ) {
        val image = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
        Image(
            painter = rememberAsyncImagePainter( image),
            contentDescription = null,
            modifier = Modifier
                .width(407.dp)
                .height(456.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(27.dp))

            ,
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(56.dp)

                .offset(y = PaddingMedium),
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = { navController.navigate(AstroflixRoutes.FirstScreen.route) })
            )
        }
    }

}

@Composable
fun Body(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = PaddingSmall, start = 4.dp, end = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center

        ) {
            movie.title?.let {
                Text(
                    text = it,
                    style = AstroFlixTypography.labelMedium,
                )
            }

        }
        Spacer(modifier = Modifier.height(PaddingSmall))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(PaddingSmall)
                .clip(RoundedCornerShape(PaddingSmall))
                .background(lightGray)
        ) {}
        Spacer(modifier = Modifier.height(PaddingSmall))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){

                Text(
                    text = movie.release_date,
                    style = AstroFlixTypography.bodySmall,
                    modifier = Modifier.padding(start = PaddingSmall)
                )

            Text(
                text = "Total votes: ${movie.vote_count}",
                style = AstroFlixTypography.bodySmall,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(PaddingMedium))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text = "Gender:  ${Genres.genres[movie.genre_ids[0]]}",
                style = AstroFlixTypography.bodySmall,
                modifier = Modifier
                    .padding(start = PaddingSmall)
                    .weight(1f)
            )
            Text(
                text = "Note: ${movie.vote_average}",
                style = AstroFlixTypography.bodySmall,
                modifier = Modifier
                    .padding(end = 46.dp)
                    .weight(1f),
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row (modifier = Modifier
            .fillMaxWidth()){

            Text(
                text = "${movie.overview}",
                style = AstroFlixTypography.bodyMedium,
                modifier = Modifier
                    .padding(start = PaddingSmall)
                    .weight(1f) ,
                textAlign = TextAlign.Left
            )


        }


    }

}
@Composable
fun StreamingAvailability(viewModel: HomeViewModel) {
    val image = viewModel.getPlatforms()

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(vertical = PaddingMedium)
    ) {
        Text(
            text = stringResource(R.string.streaming_availability),
            style = AstroFlixTypography.bodySmall,
            modifier = Modifier.padding(start = PaddingSmall),
            textAlign = TextAlign.Start
        )

        Row(modifier = Modifier.wrapContentSize()) {

            Image(
                    painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500${image}"),
                    contentDescription = null,
                    modifier = Modifier

                        .size(32.dp)
                        .offset(x = 3.dp, y = -6.dp)
                        .padding(horizontal = 4.dp),
                )

        }
    }
}



@Composable
fun FavoriteButton(viewModelFavorite: FavoriteViewModel, movie: Movie){
    val (color, image) = viewModelFavorite.buttonColor(movie)

    Column(modifier = Modifier
        .fillMaxSize()
        ,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center){

            IconButton(
                onClick = {
                    if (viewModelFavorite.isFavoriteMovie(movie)) {
                        viewModelFavorite.removeFavoriteMovie(movie)

                    } else {
                        viewModelFavorite.addFavoriteMovie(movie)

                    }


                },
                modifier = Modifier
                    .border(BorderStroke(3.dp, borderColor), shape = CircleShape)
                    .size(56.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = color),
            ) {

                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)


                )

            }

    }

}
