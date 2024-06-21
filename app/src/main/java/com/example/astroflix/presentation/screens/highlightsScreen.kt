package com.example.astroflix.presentation.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.astroflix.ui.theme.AstroFlixTypography

import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.DarkGray


import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import com.example.astroflix.R


@Composable
fun Highlights(){
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
        Header()
        BodyHighlight()

    }

}


@Composable
fun Header() {
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
                contentDescription = "Custom Image",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { }

            )
        }




            Text(
                text = stringResource(R.string.highlight_title),
                style = AstroFlixTypography.titleLarge
            )
        Spacer(modifier = Modifier.height(16.dp))



    }
}

@Composable

fun BodyHighlight() {
    val image: Painter = painterResource(id = R.drawable.harrypotter)
    Box(
        modifier = Modifier
            .size(width = 349.dp, height = 573.dp)
    ) {
        Card(
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier

                .align(Alignment.Center)
                .shadow(16.dp, RoundedCornerShape(32.dp))
        ) {
            Image(
                painter = image,
                contentDescription = "harrypotter",
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .clickable(onClick = {}),
                contentScale = ContentScale.Crop
            )
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(DarkGray, Color.Black),

                ),

                style = Stroke(
                    width = 8.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                ),
                cornerRadius = CornerRadius(32.dp.toPx())
            )
        }
        Canvas(modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f)) {
            drawRoundRect(
                brush = Brush.radialGradient(
                    colors = listOf(Color.Transparent, Color.Black),
                    center = Offset(size.width / 2, size.height / 2),
                    radius = size.maxDimension
                ),
                size = size,
                cornerRadius = CornerRadius(32.dp.toPx())
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Highlights()
}