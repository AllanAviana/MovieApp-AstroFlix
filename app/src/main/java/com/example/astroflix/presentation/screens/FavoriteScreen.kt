package com.example.astroflix.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.astroflix.R
import com.example.astroflix.ui.theme.AstroFlixTypography
import com.example.astroflix.ui.theme.darkGray
import com.example.astroflix.ui.theme.heartColor

@Composable
fun FavoriteScreen() {
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
                contentDescription = "Custom Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = back,
                contentDescription = "Custom Image",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = 32.dp, y = 32.dp)
                    .clickable { }

            )



        }

        Box(){
            BodyFavoriteScreen()

        }





    }
}

@Composable
fun BodyFavoriteScreen() {

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
            items(6) { index ->
                RowContent()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun RowContent() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        DesignImageWithButton()
        DesignImageWithButton()
    }
}

@Composable
fun DesignImageWithButton() {
    Box (modifier = Modifier.padding(top = 8.dp)){
        DesignImage()
        FavoriteScreenButton(modifier = Modifier
            .align(Alignment.TopEnd)
            .offset(y = -8.dp, x = 4.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun DesignImage() {
    val image: Painter = painterResource(id = R.drawable.harrypotter)

    ElevatedCard(
        onClick = { /* TODO */ },
        elevation = CardDefaults.elevatedCardElevation(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .size(width = 180.dp, height = 260.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                BorderStroke(2.dp, Color.Gray),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Image(
            painter = image,
            contentDescription = "Harry Potter",
            contentScale = ContentScale.Crop,
            modifier = Modifier

                .clickable(onClick = { /* TODO */ })
                .clip(RoundedCornerShape(12.dp))
        )
    }
}


@Composable
fun FavoriteScreenButton(modifier: Modifier = Modifier) {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = modifier

            .size(46.dp),
        colors = IconButtonDefaults.iconButtonColors(heartColor)
    ) {
        Image(
            painter = painterResource(id = R.drawable.heartwhite),
            contentDescription = "Streaming Service Logo",
            modifier = Modifier.size(27.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    FavoriteScreen()
}