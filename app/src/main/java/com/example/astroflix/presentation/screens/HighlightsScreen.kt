package com.example.astroflix.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import com.example.astroflix.ui.theme.AstroFlixTypography

import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.DarkGray

import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.astroflix.R
import com.example.astroflix.model.Movie
import com.example.astroflix.presentation.ViewModel.HomeViewModel
import com.example.astroflix.presentation.navigation.AstroflixRoutes
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun Highlights(
    navController: NavController,
    displayHighlightMovie: List<Movie>,
    viewModel: HomeViewModel
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DarkGray, Color.Black)
                )
            )
            .padding(24.dp)
    ) {
        Header(navController)
        BodyHighlight(displayHighlightMovie, navController, viewModel)
    }
}

@Composable
fun Header(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        val back: Painter = painterResource(id = R.drawable.back)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = back,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigateUp() }

            )
        }

        Text(
                text = stringResource(R.string.highlight_title),
                style = AstroFlixTypography.titleLarge
            )
        Spacer(modifier = Modifier.height(16.dp))

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BodyHighlight(

    displayHighlightMovie: List<Movie>,
    navController: NavController,
    viewModel: HomeViewModel
) {
    val pagerState = rememberPagerState(pageCount = { displayHighlightMovie.size })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(573.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            key = { displayHighlightMovie[it].id }
        ) { page ->

            val movieJson = Gson().toJson(displayHighlightMovie[page])
            val encodedMovieJson = URLEncoder.encode(movieJson, StandardCharsets.UTF_8.toString())
            Card(
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .shadow(16.dp, RoundedCornerShape(32.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${displayHighlightMovie[page].poster_path ?: ""}"),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(32.dp))
                        .clickable(onClick = {
                            navController.navigate("${AstroflixRoutes.SecondScreen.route}/$encodedMovieJson")
                            viewModel.platforms(displayHighlightMovie[page])
                        }),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
