package com.example.practicsing.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.practicsing.navigation.AppNavHost
import com.example.practicsing.navigation.BottomNavigationBar
import com.example.practicsing.navigation.Screen
import com.example.practicsing.main.theme.PracticSingTheme

import androidx.compose.foundation.layout.padding
import com.example.practicsing.ui.song.Test.tts.TTSProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TTSProvider.initialize(this)

        setContent {
            PracticSingTheme {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // ⭐ bottom bar가 나오면 안 되는 화면들
                val hideBottomBar = currentRoute in listOf(
                    Screen.Splash.route,
                    Screen.Login.route,
                    Screen.Register.route
                )


                Scaffold(
                    bottomBar = {
                        if (!hideBottomBar) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { paddingValues ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        TTSProvider.shutdown()
    }
}
