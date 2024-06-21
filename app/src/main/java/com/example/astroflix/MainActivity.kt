package com.example.astroflix

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

import com.example.astroflix.presentation.navegation.AstroFlixNavGraph
import com.example.astroflix.presentation.screens.HomeScreen
import com.example.astroflix.ui.theme.AstroFlixTheme


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT,
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            AstroFlixTheme {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    val navController = rememberNavController()
                    AstroFlixNavGraph(navController = navController)
                }
            }
        }
    }
}


