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
import androidx.compose.ui.graphics.painter.Painter
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
import com.example.astroflix.presentation.viewModel.FavoriteViewModel
import com.example.astroflix.presentation.navegation.AstroflixRoutes
import com.example.astroflix.ui.theme.AstroFlixTypography
import com.example.astroflix.ui.theme.borderColor
import com.example.astroflix.ui.theme.lightGray

@Composable
fun detailsScreen(
    navController: NavController,
    movie: Movie,
    viewModelFavorite: FavoriteViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Gray, Color.Black)
                )
            )
            .padding(top= 16.dp, start = 8.dp, end = 8.dp)
    ) {
        item {
            imageDetails(navController, movie)
        }
        item {
            body(movie)
        }
        item {
            Box {
                StreamingAvailability()
                FavoriteButton(viewModelFavorite, movie)
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
@Composable
fun imageDetails(navController: NavController, movie: Movie){
    Box(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .padding(top = 1.dp)
    ) {
        val image = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
        Image(
            painter = rememberAsyncImagePainter( image),
            contentDescription = "Custom Image",
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

                .offset(y = 16.dp),
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "Arrow Icon",
                tint = Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = { navController.navigate(AstroflixRoutes.FirstScreen.route) })
            )
        }
    }

}

@Composable
fun body(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 4.dp, end = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                text = movie.title,
                style = AstroFlixTypography.labelMedium,
            )

        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(lightGray)
        ) {}
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text =movie.release_date,
                style = AstroFlixTypography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "Total votes: ${movie.vote_count}",
                style = AstroFlixTypography.bodySmall,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text = "Gender:  ${Genres.genres[movie.genre_ids[0]]}",
                style = AstroFlixTypography.bodySmall,
                modifier = Modifier
                    .padding(start = 8.dp)
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
                    .padding(start = 8.dp)
                    .weight(1f) ,
                textAlign = TextAlign.Left
            )


        }


    }

}
@Composable
fun StreamingAvailability() {
    val listImages = listOf(R.drawable.netflix, R.drawable.amazon)
    Spacer(modifier = Modifier.height(16.dp))

    Row(modifier = Modifier
        .wrapContentSize()
        .padding(vertical = 16.dp)) {
        Text(
            text = stringResource(R.string.streaming_availability),
            style = AstroFlixTypography.bodySmall,
            modifier = Modifier.padding(start = 8.dp),
            textAlign = TextAlign.Start
        )



        Row(modifier = Modifier.wrapContentSize()) {
            listImages.forEach { imageResId ->
                val image: Painter = painterResource(id = imageResId)
                Image(
                    painter = image,
                    contentDescription = "Streaming Service Logo",
                    modifier = Modifier
                        .size(32.dp)
                        .offset(x = 3.dp, y = -6.dp)
                        .padding(horizontal = 4.dp)
                )
            }
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
                    contentDescription = "Streaming Service Logo",
                    modifier = Modifier
                        .size(32.dp)


                )

            }





    }



}
