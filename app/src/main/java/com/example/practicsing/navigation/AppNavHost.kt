package com.example.practicsing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.practicsing.ui.auth.LoginScreen2
import com.example.practicsing.ui.auth.RegisterScreen
import com.example.practicsing.ui.splash.SplashScreen

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
    ) {

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen2(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }
        bottomNavGraph(navController = navController)
    }
}
